package com.kbe5.rento.domain.company.controller;

import com.kbe5.rento.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.rento.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.rento.domain.company.dto.response.*;
import com.kbe5.rento.domain.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<CompanyRegisterResponse> register(@RequestBody @Valid CompanyRegisterRequest request) {
        return ResponseEntity.ok(companyService.register(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyDetail(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyDetail(id));
    }

    // 추후에 사용 될 메서드 입니다.
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getCompanyList() {
        return ResponseEntity.ok(companyService.getCompanyList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyUpdateResponse> updateCompanyInfo(@PathVariable Long id,
                                                                   @RequestBody @Valid CompanyUpdateRequest request) {
        return ResponseEntity.ok(companyService.update(id, request));
    }

    @GetMapping("/{bizNumber}")
    public ResponseEntity<Boolean> checkAvailableBizNumber(@PathVariable int bizNumber) {
        return ResponseEntity.ok(!companyService.isExistsBizNumber(bizNumber));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDeleteResponse> companyDelete(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.delete(id));
    }
}
