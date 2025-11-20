package grepp.shop.payment.application.dto;

import grepp.shop.payment.presentation.dto.PaymentRequest;

public record PaymentCommand(
        String paymentKey,
        String orderId,
        Long amount
) {
    public static PaymentCommand from(PaymentRequest request) {
        return new PaymentCommand(request.paymentKey(), request.orderId(), request.amount());
    }
}
