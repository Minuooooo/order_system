package jpabook.jpashop.domain.user.controller;

import jpabook.jpashop.config.oauth.param.KakaoLoginParams;
import jpabook.jpashop.config.oauth.param.NaverLoginParams;
import jpabook.jpashop.domain.member.dto.sign.TokenDto;
import jpabook.jpashop.domain.user.service.OAuthService;
import jpabook.jpashop.response.Response;
import jpabook.jpashop.response.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jpabook.jpashop.response.Response.*;
import static jpabook.jpashop.response.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public Response loginKakao(@RequestBody KakaoLoginParams params) {
        return success(SUCCESS_TO_SIGN_IN, oAuthService.login(params));
    }

    @PostMapping("/naver")
    public Response loginNaver(@RequestBody NaverLoginParams params) {
        return success(SUCCESS_TO_SIGN_IN, oAuthService.login(params));
    }
}
