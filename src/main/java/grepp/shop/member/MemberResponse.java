package grepp.shop.member;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MemberResponse(
        UUID id,
        String email,
        String name,
        String phone,
        String flag
) {
    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .flag(member.getFlag())
                .build();
    }
}
