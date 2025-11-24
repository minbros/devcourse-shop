package grepp.shop.payment.client;

final class TossPaymentEndpoint {
    private static final String BASE_URL =  "https://api.tosspayments.com";

    static String getConfirmUrl() {
        return BASE_URL + "/v1/payments/confirm";
    }

    private TossPaymentEndpoint() {
    }
}
