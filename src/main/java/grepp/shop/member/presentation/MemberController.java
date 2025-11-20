package grepp.shop.member.presentation;

import grepp.shop.common.ResponseEntity;
import grepp.shop.member.application.dto.MemberCommand;
import grepp.shop.member.presentation.dto.MemberRequest;
import grepp.shop.member.application.dto.MemberResponse;
import grepp.shop.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<MemberResponse>> getMembers(
            @PageableDefault(sort = "regDt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return memberService.getMembers(pageable);
    }

    @Operation(
            summary = "새로운 회원 생성",
            description = "요청에 따라 새로운 회원을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
        return memberService.createMember(MemberCommand.from(request));
    }

    @Operation(
            summary = "회원 수정",
            description = "요청에 따라 기존 회원을 수정합니다."
    )
    @PutMapping("{id}")
    public ResponseEntity<MemberResponse> updateMember(@RequestBody MemberRequest request, @PathVariable UUID id) {
        return memberService.updateMember(MemberCommand.from(request), id);
    }

    @Operation(
            summary = "회원 삭제",
            description = "요청에 따라 회원을 삭제합니다."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        return memberService.deleteMember(id);
    }
}
