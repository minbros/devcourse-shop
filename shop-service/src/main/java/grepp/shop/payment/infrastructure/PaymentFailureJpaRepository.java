package grepp.shop.payment.infrastructure;

import grepp.shop.payment.domain.PaymentFailure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PaymentFailureJpaRepository extends JpaRepository<PaymentFailure, UUID> {
}
