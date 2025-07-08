package com.kbe5.domain.manager.dto;

import com.kbe5.domain.manager.entity.Manager;
import lombok.Builder;
import lombok.Getter;

public class ManagerCommand {

    @Getter
    @Builder
    public static class Register {
        private String loginId;
        private String password;
        private String name;
        private String phone;
        private String email;
        private String companyCode;

        public Manager toEntity() {
            return Manager.builder()
                    .loginId(this.loginId)
                    .password(this.password)
                    .name(this.name)
                    .phone(this.phone)
                    .email(this.email)
                    .companyCode(this.companyCode)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Delete {

        private String loginId;
        private String password;
    }

    @Getter
    @Builder
    public static class UpdateFcmToken {

        private String fcmToken;
    }

    @Getter
    @Builder
    public static class Update {

        String name;
        String phone;
        String email;
    }
}
