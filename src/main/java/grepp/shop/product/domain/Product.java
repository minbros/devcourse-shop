package grepp.shop.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ColumnDefault("'ACTIVE'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "reg_id", nullable = false)
    private UUID regId;

    @Column(name = "reg_dt", nullable = false)
    private OffsetDateTime regDt;

    @Column(name = "modify_id", nullable = false)
    private UUID modifyId;

    @Column(name = "modify_dt", nullable = false)
    private OffsetDateTime modifyDt;
}