package grepp.shop.product.application.dto;

import grepp.shop.product.presentation.dto.ProductRequest;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductCommand(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        UUID creatorId
) {
    public static ProductCommand from(ProductRequest request) {
        return ProductCommand.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .status(request.status())
                .creatorId(request.creatorId())
                .build();
    }
}
