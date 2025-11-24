package grepp.shop.infrastructure;

import grepp.shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface MemberJpaRepository extends JpaRepository<Member, UUID> {
}
