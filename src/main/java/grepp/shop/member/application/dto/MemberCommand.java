package grepp.shop.member.application.dto;

import grepp.shop.member.presentation.dto.MemberRequest;
import lombok.Builder;

@Builder
public record MemberCommand(
        String email,
        String name,
        String password,
        String phone,
        String flag
) {
    public static MemberCommand from(MemberRequest request) {
        return MemberCommand.builder()
                .email(request.email())
                .name(request.name())
                .password(request.password())
                .phone(request.phone())
                .flag(request.flag())
                .build();
    }
}
