package com.kbe5.rento.domain.manager.controller;

import com.kbe5.rento.domain.manager.dto.request.*;
import com.kbe5.rento.domain.manager.dto.response.*;
import com.kbe5.rento.domain.manager.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managerㄴ")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/sign-up")
    public ResponseEntity<ManagerSignUpResponse> signUp(@RequestBody @Valid ManagerSignUpRequest request) {
        return ResponseEntity.ok(managerService.signUp(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerResponse> getManagerDetail(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerDetail(id));
    }

    @GetMapping("/{companyCode}")
    public ResponseEntity<List<ManagerResponse>> getManagerList(@PathVariable String companyCode) {
        return ResponseEntity.ok(managerService.getManagerList(companyCode));
    }

    @PutMapping
    public ResponseEntity<ManagerUpdateResponse> update(@RequestBody @Valid ManagerUpdateRequest request) {
        return ResponseEntity.ok(managerService.update(request));
    }

    @DeleteMapping
    public ResponseEntity<ManagerDeleteResponse> delete(@RequestBody @Valid ManagerDeleteRequest request) {
        return ResponseEntity.ok(managerService.delete(request));
    }

    @GetMapping("/{loginId}")
    public ResponseEntity<Boolean> checkAvailableLoginId(@PathVariable String loginId) {
        return ResponseEntity.ok(!managerService.isExistsLoginId(loginId));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Boolean> checkAvailableEmail(@PathVariable String email) {
        return ResponseEntity.ok(!managerService.isExistsEmail(email));
    }
}
