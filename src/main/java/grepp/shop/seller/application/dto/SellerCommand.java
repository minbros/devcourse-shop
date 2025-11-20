package grepp.shop.seller.application.dto;

import grepp.shop.seller.presentation.dto.SellerRequest;

public record SellerCommand(
        String companyName,
        String representativeName,
        String email,
        String phone,
        String businessNumber,
        String address,
        String status
) {
    public static SellerCommand from(SellerRequest request) {
        return new SellerCommand(
                request.companyName(),
                request.representativeName(),
                request.email(),
                request.phone(),
                request.businessNumber(),
                request.address(),
                request.status()
        );
    }
}
