package com.kbe5.common.util;

import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Component
public class Aes256Util {

    private final String secretKey;
    private final String IV;

    public Aes256Util(@Value("${AES_KEY}") String secretKey) {
        this.secretKey = secretKey;
        this.IV = secretKey.substring(0, 16);
    }

    public String AES_Encode(String plainText) {
        try {
            SecretKey secureKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeBase64String(encrypted);
        } catch (GeneralSecurityException e) {
            throw new DomainException(ErrorType.FAILED_DECRYPT);
        }
    }

    public String AES_Decode(String cipherText) {
        try {
            SecretKey secureKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
            byte[] decrypted = cipher.doFinal(Base64.decodeBase64(cipherText));

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new DomainException(ErrorType.FAILED_DECRYPT);
        }
    }
}