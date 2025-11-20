package grepp.shop.payment.application;

import grepp.shop.common.ResponseEntity;
import grepp.shop.payment.application.dto.PaymentCommand;
import grepp.shop.payment.application.dto.PaymentInfo;
import grepp.shop.payment.client.TossPaymentClient;
import grepp.shop.payment.client.dto.TossPaymentResponse;
import grepp.shop.payment.domain.Payment;
import grepp.shop.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final TossPaymentClient tossPaymentClient;

    public ResponseEntity<List<PaymentInfo>> getPayments(Pageable pageable) {
        List<PaymentInfo> response = paymentRepository.findAll(pageable).stream()
                .map(PaymentInfo::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK, response);
    }

    public ResponseEntity<PaymentInfo> confirm(PaymentCommand command) {
        TossPaymentResponse tossPaymentResponse = tossPaymentClient.confirm(command);
        Payment payment = Payment.from(command);

        Instant approvedAt = tossPaymentResponse.approvedAt().toInstant();
        Instant requestedAt = tossPaymentResponse.requestedAt().toInstant();
        payment.markConfirmed(tossPaymentResponse.method(), approvedAt, requestedAt);

        Payment savedPayment = paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK, PaymentInfo.from(savedPayment));
    }
}
