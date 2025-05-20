package com.kbe5.rento.domain.manager.controller;

import com.kbe5.rento.domain.manager.dto.request.*;
import com.kbe5.rento.domain.manager.dto.response.*;
import com.kbe5.rento.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/sign-up")
    public ResponseEntity<ManagerSignUpResponse> signUp(@RequestBody ManagerSignUpRequest request) {
        return ResponseEntity.ok(managerService.signUp(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDetailResponse> getManagerDetail(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerDetail(id));
    }

    @GetMapping
    public ResponseEntity<ManagerListResponse> getManagerList() {
        return ResponseEntity.ok(managerService.getManagerList());
    }

    @PutMapping
    public ResponseEntity<ManagerUpdateResponse> update(@RequestBody ManagerUpdateRequest request) {
        return ResponseEntity.ok(managerService.update(request));
    }

    @DeleteMapping
    public ResponseEntity<ManagerDeleteResponse> delete(@RequestBody ManagerDeleteRequest request) {
        return ResponseEntity.ok(managerService.delete(request));
    }
}
