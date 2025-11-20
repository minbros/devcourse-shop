package grepp.shop.member.infrastructure;

import grepp.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface MemberJpaRepository extends JpaRepository<Member, UUID> {
}
