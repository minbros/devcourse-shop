package grepp.shop.payment.application.dto;

import grepp.shop.payment.domain.PaymentFailure;

import java.time.Instant;
import java.util.UUID;

public record PaymentFailureInfo(
        UUID id,
        String orderId,
        String paymentKey,
        String errorCode,
        String errorMessage,
        Long amount,
        Instant createdAt
) {
    public static PaymentFailureInfo from(PaymentFailure paymentFailure) {
        return new PaymentFailureInfo(
                paymentFailure.getId(),
                paymentFailure.getOrderId(),
                paymentFailure.getPaymentKey(),
                paymentFailure.getErrorCode(),
                paymentFailure.getErrorMessage(),
                paymentFailure.getAmount(),
                paymentFailure.getCreatedAt()
        );
    }
}
