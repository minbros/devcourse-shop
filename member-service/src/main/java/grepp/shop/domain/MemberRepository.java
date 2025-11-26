package grepp.shop.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Page<Member> findAll(Pageable pageable);

    Optional<Member> findById(UUID uuid);

    Member save(Member member);

    void deleteById(UUID uuid);

    Optional<Member> findByEmail(String email);

    boolean existsById(UUID uuid);
}
