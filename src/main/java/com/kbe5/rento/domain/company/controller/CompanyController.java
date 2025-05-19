package com.kbe5.rento.domain.company.controller;

import com.kbe5.rento.domain.company.dto.request.CompanyDeleteRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.*;
import com.kbe5.rento.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/companys")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<CompanyRegisterResponse> register(@RequestBody CompanyRegisterRequest request) {
        return ResponseEntity.ok(companyService.register(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponse> getCompanyDetail(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyDetail(id));
    }

    @GetMapping
    public ResponseEntity<CompanyListResponse> getCompanyList() {
        return ResponseEntity.ok(companyService.getCompanyList());
    }

    @PutMapping
    public ResponseEntity<CompanyUpdateResponse> update(@RequestBody CompanyUpdateRequest request) {
        return ResponseEntity.ok(companyService.update(request));
    }
}
