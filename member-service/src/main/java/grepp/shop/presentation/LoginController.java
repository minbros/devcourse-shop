package grepp.shop.presentation;

import grepp.shop.application.MemberService;
import grepp.shop.common.ResponseEntity;
import grepp.shop.presentation.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}")
public class LoginController {
    private final MemberService memberService;

    @PostMapping("login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginRequest loginRequest){
        return memberService.login(loginRequest);
    }

    @GetMapping("authorizations/check")
    public Boolean check(@RequestParam("httpMethod") String httpMethod, @RequestParam("requestPath") String requestPath){
        return memberService.check(httpMethod, requestPath);
    }
}
