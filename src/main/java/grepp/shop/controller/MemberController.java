package grepp.shop.controller;

import grepp.shop.common.ResponseEntity;
import grepp.shop.member.MemberRequest;
import grepp.shop.member.MemberResponse;
import grepp.shop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(
            summary = "회원 목록 조회",
            description = "모든 회원의 정보를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> members = memberService.getMembers();
        return new ResponseEntity<>(HttpStatus.OK.value(), members.size(), members);
    }

    @Operation(
            summary = "새로운 회원 생성",
            description = "요청에 따라 새로운 회원을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, response);
    }

    @Operation(
            summary = "회원 수정",
            description = "요청에 따라 기존 회원을 수정합니다."
    )
    @PutMapping("{id}")
    public ResponseEntity<MemberResponse> updateMember(@RequestBody MemberRequest request, @PathVariable UUID id) {
        MemberResponse response = memberService.updateMember(request, id);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, response);
    }

    @Operation(
            summary = "회원 삭제",
            description = "요청에 따라 회원을 삭제합니다."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }
}
