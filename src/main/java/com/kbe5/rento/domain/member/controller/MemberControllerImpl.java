package com.kbe5.rento.domain.member.controller;

import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.rento.domain.member.entity.Position;
import com.kbe5.rento.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController {

    private final MemberService memberService;

    @Override
    @PostMapping
    public void register(@RequestBody @Validated MemberRegisterRequest request) {
        memberService.register(request);
    }

    @Override
    @PutMapping("/{memberId}")
    public void update(@PathVariable Long memberId, @RequestBody @Validated MemberUpdateRequest request) {
        memberService.update(request, memberId);
    }

    @Override
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> delete(@PathVariable Long memberId) {
        memberService.delete(memberId);

        return ResponseEntity.ok().body(memberId + " 지워졌다");
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MemberInfoResponse>> getUsers(@RequestParam String companyCode) {
        List<MemberInfoResponse> memberInfoResponses = memberService.getMemberList(companyCode);

        return ResponseEntity.ok(memberInfoResponses);
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponse> getUser(@PathVariable Long memberId) {
        MemberInfoResponse memberInfoResponses = memberService.getMember(memberId);

        return ResponseEntity.ok(memberInfoResponses);
    }

    @GetMapping("/positions")
    public ResponseEntity<List<String>> getPositions() {
        return ResponseEntity.ok(Position.getPositions());
    }
}
