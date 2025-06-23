package com.kbe5.api.domain.company.vo;

import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.domain.company.entity.Company;
import lombok.Getter;

@Getter
public class CompanyUpdateVO {

    private final int bizNumber;
    private final String name;

    public CompanyUpdateVO(CompanyUpdateRequest request) {
        this.bizNumber = request.bizNumber();
        this.name = request.name();
    }

    public void update(Company company) {
        company.update(bizNumber, name);
    }
}
