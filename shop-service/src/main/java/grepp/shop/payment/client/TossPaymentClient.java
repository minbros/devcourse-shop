package grepp.shop.payment.client;

import grepp.shop.payment.application.dto.PaymentCommand;
import grepp.shop.payment.client.dto.TossPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient {
    private final RestTemplate restTemplate;
    private final TossPaymentProperties properties;

    public TossPaymentResponse confirm(PaymentCommand command) {
        String paymentKey = command.paymentKey();
        if (paymentKey == null || paymentKey.isBlank()) {
            throw new IllegalStateException("Empty payment key");
        }

        HttpHeaders headers = createHeaders();
        Map<String, Object> body = Map.of(
                "paymentKey", command.paymentKey(),
                "orderId", command.orderId(),
                "amount", command.amount()
        );
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        log.info("[Toss] Confirm entity: {}", entity);

        try {
            return restTemplate.postForObject(TossPaymentEndpoint.getConfirmUrl(), entity, TossPaymentResponse.class);
        } catch (HttpStatusCodeException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            throw new IllegalStateException("Toss confirm failed (" + statusCode + "): " + responseBody, e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = properties.getSecretKey() + ":";
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
        return headers;
    }
}
