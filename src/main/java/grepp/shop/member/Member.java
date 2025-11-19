package grepp.shop.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "\"member\"", schema = "public")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "\"name\"", length = 20)
    private String name;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "reg_id", nullable = false)
    private UUID regId;

    @Column(name = "reg_dt", nullable = false)
    private OffsetDateTime regDt;

    @Column(name = "modify_id", nullable = false)
    private UUID modifyId;

    @Column(name = "modify_dt", nullable = false)
    private OffsetDateTime modifyDt;

    @Column(name = "saltkey", nullable = false, length = 14)
    private String saltKey;

    @Column(name = "flag", length = 5)
    private String flag;

    public void update(MemberRequest request) {
        email = request.email();
        name = request.name();
        password = request.password();
        phone = request.phone();
        saltKey = request.saltKey();
        flag = request.flag();
    }

    @PrePersist
    public void prePersist() {
        if (regId == null) {
            regId = id != null ? id : UUID.randomUUID();
        }
        if (modifyId == null) {
            modifyId = regId;
        }
        if (regDt == null) {
            regDt = OffsetDateTime.now();
        }
        if (modifyDt == null) {
            modifyDt = OffsetDateTime.now();
        }
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @PreUpdate
    public void preUpdate() {
        modifyDt = OffsetDateTime.now();
        if (modifyId == null) {
            modifyId = id;
        }
    }
}
