package grepp.shop.infrastructure;

import grepp.shop.domain.Member;
import grepp.shop.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return memberJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Member> findById(UUID uuid) {
        return memberJpaRepository.findById(uuid);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void deleteById(UUID uuid) {
        memberJpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return memberJpaRepository.existsById(uuid);
    }
}
