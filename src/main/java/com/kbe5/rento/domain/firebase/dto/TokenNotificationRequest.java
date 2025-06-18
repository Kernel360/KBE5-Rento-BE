package com.kbe5.rento.domain.firebase.dto;

public record TokenNotificationRequest(
    String title,
    String content,
    String url
) {
}
