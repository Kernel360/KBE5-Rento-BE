package com.kbe5.rento.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kbe5.rento.domain.drive.repository.DriveRepository;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableRetry
@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
class FcmServiceTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private DriveRepository driveRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @InjectMocks
    private FcmService fcmService;

    private List<Manager> managers;
    private TokenNotificationRequest request;

    @BeforeEach
    void setUp() {
        Manager manager1 = mock(Manager.class);
        Manager manager2 = mock(Manager.class);

        when(manager1.getFcmToken()).thenReturn("token1");
        when(manager2.getFcmToken()).thenReturn("token2");

        managers = Arrays.asList(manager1, manager2);

        request = new TokenNotificationRequest(
                "제목",
                "내용",
                "url"
        );
    }

    @Test
    @DisplayName("재시도 성공 테스트")
    void 재시도_성공_테스트() throws FirebaseMessagingException {
        // given
        FirebaseMessaging mockFirebaseMessaging = mock(FirebaseMessaging.class);

        FirebaseMessagingException exception = mock(FirebaseMessagingException.class);

        when(mockFirebaseMessaging.send(any(Message.class)))
                .thenThrow(exception)
                .thenReturn("success-message-id");

        FcmService fcmService = new FcmService(managerRepository, driveRepository, mockFirebaseMessaging);

        assertDoesNotThrow(() -> fcmService.send(request, managers));

        verify(mockFirebaseMessaging, atLeast(2)).send(any(Message.class));
    }
}
