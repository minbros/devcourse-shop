package grepp.shop.application.dto;

import grepp.shop.presentation.dto.MemberRequest;
import lombok.Builder;

@Builder
public record MemberCommand(
        String email,
        String name,
        String password,
        String phone
) {
    public static MemberCommand from(MemberRequest request) {
        return MemberCommand.builder()
                .email(request.email())
                .name(request.name())
                .password(request.password())
                .phone(request.phone())
                .build();
    }
}
