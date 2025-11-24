package grepp.shop.product.domain;

import grepp.shop.product.application.dto.ProductCommand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product", schema = "public")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private int stock;

    @ColumnDefault("'ACTIVE'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "reg_id", nullable = false)
    private UUID regId;

    @CreatedDate
    @Column(name = "reg_dt", nullable = false)
    private Instant regDt;

    @Column(name = "modify_id", nullable = false)
    private UUID modifyId;

    @LastModifiedDate
    @Column(name = "modify_dt", nullable = false)
    private Instant modifyDt;

    public static Product from(ProductCommand command) {
        return Product.builder()
                .sellerId(command.sellerId())
                .name(command.name())
                .description(command.description())
                .price(command.price())
                .stock(command.stock())
                .status(command.status())
                .regId(command.operatorId())
                .modifyId(command.operatorId())
                .build();
    }

    public void update(ProductCommand command) {
        name = command.name();
        description = command.description();
        price = command.price();
        stock = command.stock();
        status = command.status();
        modifyId = command.operatorId();
    }
}
