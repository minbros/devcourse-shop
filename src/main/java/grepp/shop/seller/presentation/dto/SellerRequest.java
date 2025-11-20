package grepp.shop.seller.presentation.dto;

public record SellerRequest(
        String companyName,
        String representativeName,
        String email,
        String phone,
        String businessNumber,
        String address,
        String status
) {
}
