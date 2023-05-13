package jpabook.jpashop.domain.user.service;

import jpabook.jpashop.config.jwt.JwtProvider;
import jpabook.jpashop.config.oauth.common.RequestOAuthInfoService;
import jpabook.jpashop.config.oauth.common.OAuthInfoResponse;
import jpabook.jpashop.config.oauth.common.OAuthLoginParams;
import jpabook.jpashop.domain.member.dto.sign.TokenDto;
import jpabook.jpashop.domain.member.dto.sign.TokenResponseDto;
import jpabook.jpashop.domain.user.entity.User;
import jpabook.jpashop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenResponseDto login(OAuthLoginParams params) {

        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        TokenDto tokenDto = jwtProvider.generateByEmail(findOrCreateUser(oAuthInfoResponse));

        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private String findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getEmail)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private String newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getEmail();
    }
}
