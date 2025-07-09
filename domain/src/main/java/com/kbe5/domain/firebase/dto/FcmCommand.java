package com.kbe5.domain.firebase.dto;

import lombok.Builder;
import lombok.Getter;

public class FcmCommand {

    @Getter
    @Builder
    public static class NotificationRequest{
        private final String title;
        private final String body;
        private final String url;
    }
}
