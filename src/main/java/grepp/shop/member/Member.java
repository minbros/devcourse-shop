package grepp.shop.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "\"member\"", schema = "public")
public class Member {
    @Schema(name = "유저의 UUID")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(name = "이메일 주소")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Schema(name = "유저 이름")
    @Column(name = "\"name\"", length = 20)
    private String name;

    @Schema(name = "비밀번호")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Schema(name = "전화번호")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Schema(name = "등록한 UUID")
    @Column(name = "reg_id", nullable = false)
    private UUID regId;

    @Schema(name = "등록한 날짜")
    @CreatedDate
    @Column(name = "reg_dt", nullable = false)
    private Instant regDt;

    @Schema(name = "수정한 UUID")
    @Column(name = "modify_id", nullable = false)
    private UUID modifyId;

    @Schema(name = "수정한 날짜")
    @LastModifiedDate
    @Column(name = "modify_dt", nullable = false)
    private Instant modifyDt;

    @Schema(name = "비밀번호용 salt key")
    @Column(name = "saltkey", nullable = false, length = 14)
    private String saltKey;

    @Schema(name = "유저의 상태")
    @Column(name = "flag", length = 5)
    private String flag;

    public static Member from(MemberRequest request) {
        return Member.builder()
                .email(request.email())
                .name(request.name())
                .password(request.password())
                .flag(request.flag())
                .phone(request.phone())
                .build();
    }

    public void update(MemberRequest request) {
        if (request.email() != null) email = request.email();
        if (request.name() != null) name = request.name();
        if (request.password() != null) password = request.password();
        if (request.phone() != null) phone = request.phone();
        if (request.flag() != null) flag = request.flag();
    }

    @PrePersist
    public void prePersist() {
        if (regId == null) {
            regId = id != null ? id : UUID.randomUUID();
        }
        if (modifyId == null) {
            modifyId = regId;
        }
        if (saltKey == null) {
            saltKey = generateSaltKey();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (modifyId == null) {
            modifyId = id;
        }
    }

    private static String generateSaltKey() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        return String.format("%04d%02d%02d%02d%02d%02d",
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond()
        );
    }
}
