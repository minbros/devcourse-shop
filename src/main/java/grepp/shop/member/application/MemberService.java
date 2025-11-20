package grepp.shop.member.application;

import grepp.shop.common.ResponseEntity;
import grepp.shop.member.domain.Member;
import grepp.shop.member.domain.MemberRepository;
import grepp.shop.member.application.dto.MemberCommand;
import grepp.shop.member.application.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> getMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        List<MemberResponse> response = page.stream()
                .map(MemberResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), page.getNumberOfElements(), response);
    }

    public ResponseEntity<MemberResponse> createMember(MemberCommand command) {
        Member member = Member.from(command);
        Member createdMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, MemberResponse.from(createdMember));
    }

    public ResponseEntity<MemberResponse> updateMember(MemberCommand request, UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        member.update(request);
        Member updatedMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, MemberResponse.from(updatedMember));
    }

    public ResponseEntity<Void> deleteMember(UUID id) {
        memberRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }
}
