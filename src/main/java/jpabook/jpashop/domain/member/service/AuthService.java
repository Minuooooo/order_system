package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.jwt.JwtProvider;
import jpabook.jpashop.config.oauth.RequestOAuthInfoService;
import jpabook.jpashop.config.oauth.common.OAuthLoginParams;
import jpabook.jpashop.config.redis.RedisService;
import jpabook.jpashop.domain.member.dto.sign.*;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.exception.situation.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final JwtProvider jwtProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public UsernameResponseDto findEmailBySocial(OAuthLoginParams oAuthLoginParams) {
        return UsernameResponseDto.toDto(requestOAuthInfoService.request(oAuthLoginParams).getEmail());
    }

    public void validateDuplicate(ValidateSignUpRequestDto validateSignUpRequestDto) {
        validateDuplicateByUsername(validateSignUpRequestDto);
    }

    public void signUp(SignUpRequestDto req) {
        validateSignUpInfo(req);  // TODO 소셜 로그인 계정이 이미 있을 때 가입할 경우 휴대전화 인증을 통해 계정 존재 유무 알려주기
        memberRepository.save(req.toEntity(passwordEncoder));
    }

    public TokenResponseDto signInWithGeneral(LoginRequestDto req) {
        return authorize(req.getUsername());
    }

    public TokenResponseDto signInWithSocial(OAuthLoginParams oAuthLoginParams) {
        return authorize(requestOAuthInfoService.request(oAuthLoginParams).getEmail());
    }

    public void logout(Member member) {
        redisService.deleteValues("RT: " + member.getUsername());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        validateRefreshToken(tokenRequestDto);
        Authentication authentication = jwtProvider.getAuthentication(tokenRequestDto.getAccessToken());
        validateRefreshTokenOwner(authentication, tokenRequestDto);
        TokenDto tokenDto = getReadyForAuthorize(authentication);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private void validateDuplicateByUsername(ValidateSignUpRequestDto validateSignUpRequestDto) {
        if (memberRepository.existsByUsername(validateSignUpRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException(validateSignUpRequestDto.getUsername());
        }
    }

    private void validateSignUpInfo(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException(signUpRequestDto.getUsername());
        }
    }

    private TokenDto getReadyForAuthorize(Authentication authentication) {
        TokenDto tokenDto = jwtProvider.generateTokenDto(authentication);
        redisService.setValues("RT: " + authentication.getName(), tokenDto.getRefreshToken(), Duration.ofMillis(tokenDto.getRefreshTokenExpiresIn()));
        return tokenDto;
    }

//    private Authentication getUserAuthenticationByGeneral(LoginRequestDto req) {
//        return authenticationManagerBuilder.getObject().authenticate(req.toAuthentication());
//    }

    private Authentication getUserAuthenticationBySocial(String email) {
        return authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(email, email));
    }

    private TokenResponseDto authorize(String email) {
        String foundRefreshToken = redisService.getValues("RT: " + email);
        Authentication authentication = getUserAuthenticationBySocial(email);    // TODO 토큰에 넣을 정보 고민 현재 (email, email)
        long now = (new Date()).getTime();

        if (!StringUtils.hasText(foundRefreshToken)) {
            TokenDto tokenDto = getReadyForAuthorize(authentication);
            return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        }
        return new TokenResponseDto(jwtProvider.generateAccessToken(authentication, now), foundRefreshToken);
    }

    private void validateRefreshToken(TokenRequestDto tokenRequestDto) {
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
    }

    private void validateRefreshTokenOwner(Authentication authentication, TokenRequestDto tokenRequestDto) {
        if (!redisService.getValues("RT: " + authentication.getName()).equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }
}
