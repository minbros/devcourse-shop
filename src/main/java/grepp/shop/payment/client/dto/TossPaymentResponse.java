package grepp.shop.payment.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TossPaymentResponse(
        String paymentKey,
        String orderId,
        Long totalAmount,
        String method,
        String status,
        OffsetDateTime requestedAt,
        OffsetDateTime approvedAt
) {
}
