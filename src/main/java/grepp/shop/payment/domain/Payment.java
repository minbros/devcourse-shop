package grepp.shop.payment.domain;

import grepp.shop.payment.application.dto.PaymentCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "payment_key", nullable = false, length = 200)
    private String paymentKey;

    @NotNull
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Long amount;

    @Column(name = "method", length = 50)
    private String method;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "requested_at")
    private Instant requestedAt;

    @Column(name = "approved_at")
    private Instant approvedAt;

    @Size(max = 255)
    @Column(name = "fail_reason")
    private String failReason;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public static Payment from(PaymentCommand command) {
        return Payment.builder()
                .paymentKey(command.paymentKey())
                .orderId(command.orderId())
                .amount(command.amount())
                .build();
    }

    public void markConfirmed(String method, Instant approvedAt, Instant requestedAt) {
        this.method = method;
        this.status = PaymentStatus.CONFIRMED;
        this.approvedAt = approvedAt;
        this.requestedAt = requestedAt;
        this.failReason = null;
    }

    @PrePersist
    protected void prePersist() {
        if (status == null) {
            status = PaymentStatus.READY;
        }
    }
}
