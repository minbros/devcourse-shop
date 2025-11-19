package grepp.shop.service;

import grepp.shop.member.Member;
import grepp.shop.member.MemberRepository;
import grepp.shop.member.MemberRequest;
import grepp.shop.member.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = Member.from(request);
        memberRepository.save(member);
        return MemberResponse.from(member);
    }

    public MemberResponse updateMember(MemberRequest request, UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        member.update(request);
        return MemberResponse.from(member);
    }

    public void deleteMember(UUID id) {
        memberRepository.deleteById(id);
    }
}
