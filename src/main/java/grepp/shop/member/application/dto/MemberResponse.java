package grepp.shop.member.application.dto;

import grepp.shop.member.domain.Member;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MemberResponse(
        UUID id,
        String email,
        String name,
        String phone,
        String flag,
        Instant regDt,
        Instant modifyDt
) {
    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .flag(member.getFlag())
                .regDt(member.getRegDt())
                .modifyDt(member.getModifyDt())
                .build();
    }
}
