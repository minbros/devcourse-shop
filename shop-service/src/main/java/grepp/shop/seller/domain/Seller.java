package grepp.shop.seller.domain;

import grepp.shop.seller.application.dto.SellerCommand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "representative_name", nullable = false, length = 50)
    private String representativeName;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "business_number", nullable = false, length = 20)
    private String businessNumber;

    @Column(name = "address", length = 200)
    private String address;

    @ColumnDefault("'ACTIVE'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public static Seller from(SellerCommand command) {
        return Seller.builder()
                .companyName(command.companyName())
                .representativeName(command.representativeName())
                .email(command.email())
                .phone(command.phone())
                .businessNumber(command.businessNumber())
                .address(command.address())
                .status(command.status())
                .build();
    }

    public void update(SellerCommand command) {
        companyName = command.companyName();
        representativeName = command.representativeName();
        email = command.email();
        phone = command.phone();
        businessNumber = command.businessNumber();
        address = command.address();
        status = command.status();
    }
}
