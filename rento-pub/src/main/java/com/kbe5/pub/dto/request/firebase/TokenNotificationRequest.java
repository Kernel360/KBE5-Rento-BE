package com.kbe5.pub.dto.request.firebase;

public record TokenNotificationRequest(
        String title,
        String content,
        String url
) {
}