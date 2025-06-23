package com.kbe5.domain.commonservice.firebase.dto;

public record TokenNotificationRequest(
    String title,
    String content,
    String url
) {
}
