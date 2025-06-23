package com.kbe5.adapter.dto.request.firebase;

public record TokenNotificationRequest(
        String title,
        String content,
        String url
) {
}