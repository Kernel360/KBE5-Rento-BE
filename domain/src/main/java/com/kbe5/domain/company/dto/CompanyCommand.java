package com.kbe5.domain.company.dto;

import com.kbe5.domain.company.entity.Company;
import lombok.Builder;
import lombok.Getter;

public class CompanyCommand {

    @Getter
    @Builder
    public static class Register {
        private String name;
        private int bizNumber;

        public Company toEntity() {
            return Company.builder()
                    .name(this.name)
                    .bizNumber(this.bizNumber)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Update {
        private String name;
        private int bizNumber;

        public void applyTo(Company company) {
            company.update(this.name, this.bizNumber);
        }
    }

    @Getter
    @Builder
    public static class CheckBizNumber {
        private int bizNumber;
    }

    @Getter
    @Builder
    public static class Delete {
        private Long id;
    }
}