package jpabook.jpashop.domain.user.controller;

import jpabook.jpashop.config.oauth.param.KakaoLoginParams;
import jpabook.jpashop.config.oauth.param.NaverLoginParams;
import jpabook.jpashop.domain.member.dto.sign.TokenDto;
import jpabook.jpashop.domain.user.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {

    private final OAuthService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    public ResponseEntity<TokenDto> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
