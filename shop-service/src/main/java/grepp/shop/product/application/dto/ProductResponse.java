package grepp.shop.product.application.dto;

import grepp.shop.product.domain.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProductResponse(
        UUID id,
        UUID sellerId,
        String name,
        String description,
        BigDecimal price,
        int stock,
        String status,
        Instant regDt,
        Instant modifyDt
) {
    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .regDt(product.getRegDt())
                .modifyDt(product.getModifyDt())
                .build();
    }
}
