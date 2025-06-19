package com.kbe5.rento.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class FCMInitializer {

    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void init() {
        try (var inputStream = new ClassPathResource(firebaseConfigPath).getInputStream()) {
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
}
