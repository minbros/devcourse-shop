package grepp.shop.seller.application.dto;

import grepp.shop.seller.domain.Seller;

import java.time.Instant;
import java.util.UUID;

public record SellerResponse(
        UUID id,
        String companyName,
        String representativeName,
        String email,
        String phone,
        String businessNumber,
        String address,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
    public static SellerResponse from(Seller seller) {
        return new SellerResponse(
                seller.getId(),
                seller.getCompanyName(),
                seller.getRepresentativeName(),
                seller.getEmail(),
                seller.getPhone(),
                seller.getBusinessNumber(),
                seller.getAddress(),
                seller.getStatus(),
                seller.getCreatedAt(),
                seller.getUpdatedAt()
        );
    }
}
