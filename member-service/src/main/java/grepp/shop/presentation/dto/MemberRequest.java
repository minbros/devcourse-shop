package grepp.shop.presentation.dto;

public record MemberRequest(
        String email,
        String name,
        String password,
        String phone
) {
}
