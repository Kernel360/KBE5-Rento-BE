package com.kbe5.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FCMInitializer {

    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void init() {
        try (InputStream inputStream = getFirebaseConfigInputStream()) {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase 앱이 초기화되었습니다");
            }
        } catch (IOException e) {
            log.error("Firebase 초기화 실패: {}", e.getMessage(), e);
        }
    }

    private InputStream getFirebaseConfigInputStream() throws IOException {
        if (firebaseConfigPath.startsWith("/") || firebaseConfigPath.startsWith("./")) {
            return new FileInputStream(firebaseConfigPath);
        } else {
            return new ClassPathResource(firebaseConfigPath).getInputStream();
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(FirebaseApp.getInstance());
    }
}
