package com.kbe5.api.domain.firebase.dto;

public record TokenNotificationRequest(
    String title,
    String content,
    String url
) {
}
