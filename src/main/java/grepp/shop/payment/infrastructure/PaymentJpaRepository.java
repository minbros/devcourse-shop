package grepp.shop.payment.infrastructure;

import grepp.shop.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
}
