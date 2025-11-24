package grepp.shop.payment.application.dto;

import grepp.shop.payment.domain.Payment;
import grepp.shop.payment.domain.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentInfo(
        UUID id,
        String orderId,
        String paymentKey,
        Long amount,
        PaymentStatus status,
        String method,
        Instant requestedAt,
        Instant approvedAt,
        String failReason
) {
    public static PaymentInfo from(Payment payment) {
        return new PaymentInfo(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentKey(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getMethod(),
                payment.getRequestedAt(),
                payment.getApprovedAt(),
                payment.getFailReason()
        );
    }
}
