package grepp.shop.payment.presentation.dto;

public record PaymentFailureRequest(
        String orderId,
        String paymentKey,
        String errorCode,
        String errorMessage,
        Long amount,
        String rawPayload
) {
}
