package grepp.shop.payment.infrastructure;

import grepp.shop.payment.domain.PaymentFailure;
import grepp.shop.payment.domain.PaymentFailureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentFailureRepositoryAdapter implements PaymentFailureRepository {
    private final PaymentFailureJpaRepository paymentFailureJpaRepository;

    @Override
    public PaymentFailure save(PaymentFailure paymentFailure) {
        return paymentFailureJpaRepository.save(paymentFailure);
    }
}
