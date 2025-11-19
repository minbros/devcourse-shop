package grepp.shop.service;

import grepp.shop.common.ResponseEntity;
import grepp.shop.member.Member;
import grepp.shop.member.MemberRepository;
import grepp.shop.member.MemberRequest;
import grepp.shop.member.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), response.size(), response);
    }

    public ResponseEntity<MemberResponse> createMember(MemberRequest request) {
        Member member = Member.from(request);
        Member createdMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, MemberResponse.from(createdMember));
    }

    public ResponseEntity<MemberResponse> updateMember(MemberRequest request, UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        member.update(request);
        Member updatedMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, MemberResponse.from(updatedMember));
    }

    public ResponseEntity<Void> deleteMember(UUID id) {
        memberRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }
}
