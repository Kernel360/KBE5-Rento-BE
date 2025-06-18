package com.kbe5.rento.domain.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record TokenNotificationRequest(
    String title,
    String content,
    //todo: 관련된 url을 첨부할 것인지?
    String url
) {
}
