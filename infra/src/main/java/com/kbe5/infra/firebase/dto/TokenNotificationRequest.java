package com.kbe5.infra.firebase.dto;

public record TokenNotificationRequest(
    String title,
    String content,
    String url
) {
}
