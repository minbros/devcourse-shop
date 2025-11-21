package grepp.shop.payment.domain;

import grepp.shop.payment.application.dto.PaymentFailureCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment_failure")
public class PaymentFailure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Size(max = 200)
    @Column(name = "payment_key", length = 200)
    private String paymentKey;

    @Size(max = 50)
    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Size(max = 255)
    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "raw_payload", columnDefinition = "text")
    private String rawPayload;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public static PaymentFailure from(PaymentFailureCommand command) {
        return PaymentFailure.builder()
                .orderId(command.orderId())
                .paymentKey(command.paymentKey())
                .errorCode(command.errorCode())
                .errorMessage(command.errorMessage())
                .amount(command.amount())
                .rawPayload(command.rawPayload())
                .build();
    }
}
