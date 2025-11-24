package grepp.shop.product.presentation.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        UUID sellerId,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        UUID operatorId
) {
}
