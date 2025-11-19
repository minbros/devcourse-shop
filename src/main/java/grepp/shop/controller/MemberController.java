package grepp.shop.controller;

import grepp.shop.member.Member;
import grepp.shop.member.MemberRepository;
import grepp.shop.member.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/members")
public class MemberController {
    private final MemberRepository memberRepository;

    @Operation(
            summary = "회원 목록 조회",
            description = "모든 회원의 정보를 조회합니다."
    )
    @GetMapping
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Operation(
            summary = "새로운 회원 생성",
            description = "요청에 따라 새로운 회원을 생성합니다."
    )
    @PostMapping
    public Member create(@RequestBody MemberRequest request) {
        Member member = Member.builder()
                .email(request.email())
                .name(request.name())
                .phone(request.phone())
                .flag(request.flag())
                .saltKey(request.saltKey())
                .password(request.password())
                .build();

        return memberRepository.save(member);
    }

    @Operation(
            summary = "회원 수정",
            description = "요청에 따라 기존 회원을 수정합니다."
    )
    @PutMapping("{id}")
    public Member update(@RequestBody MemberRequest request, @PathVariable UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        member.update(request);
        return memberRepository.save(member);
    }

    @Operation(
            summary = "회원 삭제",
            description = "요청에 따라 회원을 삭제합니다."
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id) {
        memberRepository.deleteById(id);
    }
}
