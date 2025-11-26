package grepp.shop.application;

import grepp.shop.application.dto.MemberCommand;
import grepp.shop.application.dto.MemberResponse;
import grepp.shop.application.dto.TokenResponse;
import grepp.shop.common.ResponseEntity;
import grepp.shop.domain.Member;
import grepp.shop.domain.MemberRepository;
import grepp.shop.presentation.dto.LoginRequest;
import grepp.shop.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    public ResponseEntity<List<MemberResponse>> getMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        List<MemberResponse> response = page.stream()
                .map(MemberResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), page.getNumberOfElements(), response);
    }

    public ResponseEntity<MemberResponse> createMember(MemberCommand command) {
        String encodedPassword = passwordEncoder.encode(command.password());
        Member member = Member.from(command, encodedPassword);
        Member createdMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), 1, MemberResponse.from(createdMember));
    }

    public ResponseEntity<MemberResponse> updateMember(MemberCommand command, UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        String password = command.password();
        String encodedPassword = (password == null || password.isBlank())
                ? member.getPassword()
                : passwordEncoder.encode(password);

        member.update(command, encodedPassword);
        Member updatedMember = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, MemberResponse.from(updatedMember));
    }

    public ResponseEntity<Void> deleteMember(UUID id) {
        memberRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), 0, null);
    }

    public ResponseEntity<TokenResponse> login(LoginRequest loginRequest) {
        Optional<Member> memberOptional = memberRepository.findByEmail(loginRequest.email());

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId().toString(), null);
                TokenResponse token = new TokenResponse(jwtProvider.generateToken(authentication), jwtProvider.generateRefreshToken(authentication));
                return new ResponseEntity<>(HttpStatus.OK.value(), 1, token);
            } else {
                throw new IllegalArgumentException("password is not correct");
            }
        }
        return null;
    }

    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-accessToken");
        String subject = jwtProvider.getUserData(refreshToken);
        UUID id = UUID.fromString(subject);
        if (!memberRepository.existsById(id)) {
            throw new IllegalStateException("Invalid accessToken for finding member");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(id, null);
        TokenResponse tokenResponse =
                new TokenResponse(jwtProvider.generateToken(authentication), refreshToken);
        return new ResponseEntity<>(HttpStatus.OK.value(), 1, tokenResponse);
    }

    public boolean check(String ignored1, String ignored2) {
        return true;
    }
}
