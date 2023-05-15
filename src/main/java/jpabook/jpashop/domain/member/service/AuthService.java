package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.jwt.JwtProvider;
import jpabook.jpashop.config.oauth.common.OAuthInfoResponse;
import jpabook.jpashop.config.oauth.common.OAuthLoginParams;
import jpabook.jpashop.config.oauth.RequestOAuthInfoService;
import jpabook.jpashop.config.redis.RedisService;
import jpabook.jpashop.domain.member.dto.sign.*;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Authority;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.exception.situation.LoginFailureException;
import jpabook.jpashop.exception.situation.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
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

    public Member signUp(SignUpRequestDto req) {
        validateSignUpInfo(req);  // TODO 소셜 로그인 계정이 이미 있을 때 가입할 경우 휴대전화 인증을 통해 계정 존재 유무 알려주기
        Member member = createSignupFormOfUser(req);
        memberRepository.save(member);
        return member;
    }

    public TokenResponseDto signInWithGeneral(LoginRequestDto req) {
        Member member = validateExistsByUsername(req.getUsername());
        validatePassword(req, member);

        Authentication authentication = getUserAuthenticationByGeneral(req);
        TokenDto tokenDto = jwtProvider.generateTokenDto(authentication);
        Duration duration = Duration.ofMillis(tokenDto.getRefreshTokenExpiresIn());
        redisService.setValues("RT: " + authentication.getName(), tokenDto.getRefreshToken(), duration);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public TokenResponseDto signInWithSocial(OAuthLoginParams oAuthLoginParams) {

        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(oAuthLoginParams);

        validateExistsByUsername(oAuthInfoResponse.getEmail());

        Authentication authentication = getUserAuthenticationBySocial(oAuthInfoResponse.getEmail());  // TODO 토큰에 넣을 정보 고민
        TokenDto tokenDto = jwtProvider.generateTokenDto(authentication);
        Duration duration = Duration.ofMillis(tokenDto.getRefreshTokenExpiresIn());
        redisService.setValues("RT: " + authentication.getName(), tokenDto.getRefreshToken(), duration);

        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public void logout(Member member) {
        redisService.deleteValues("RT: " + member.getUsername());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        validateRefreshToken(tokenRequestDto);

        Authentication authentication = jwtProvider.getAuthentication(tokenRequestDto.getAccessToken());

        validateExistsByRefreshToken(authentication);
        validateRefreshTokenOwner(authentication, tokenRequestDto);

        TokenDto tokenDto = jwtProvider.generateTokenDto(authentication);
        Duration duration = Duration.ofMillis(tokenDto.getRefreshTokenExpiresIn());
        redisService.setValues("RT: " + authentication.getName(), tokenDto.getRefreshToken(), duration);

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

    private Address getAddress(SignUpRequestDto req) {
        return Address.builder()
                .city(req.getCity())
                .street(req.getStreet())
                .zipcode(req.getZipcode())
                .build();
    }

    private Member createSignupFormOfUser(SignUpRequestDto req) {

        return Member.builder()
                .username(req.getUsername())
                .password(validateExistsByPassword(req))  // 일반, 소셜 회원가입을 비밀번호 유무로 구분
                .name(req.getName())
                .address(getAddress(req))
                .profileImageUrl("basic.png")  // TODO S3에 이미지 저장 후, 확장자 추가 (EX. basic.JPEG)
                .authority(Authority.ROLE_USER)
                .build();
    }

    private String validateExistsByPassword(SignUpRequestDto req) {
        if (!StringUtils.hasText(req.getPassword())) {
            return passwordEncoder.encode(UUID.randomUUID().toString());
        } else {
            return passwordEncoder.encode(req.getPassword());
        }
    }

    private Member validateExistsByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new LoginFailureException();
        });
    }

    private void validatePassword(LoginRequestDto loginRequestDto, Member member) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private Authentication getUserAuthenticationByGeneral(LoginRequestDto req) {
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    private Authentication getUserAuthenticationBySocial(String email) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, email);
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    private void validateRefreshToken(TokenRequestDto tokenRequestDto) {
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
    }

    private void validateExistsByRefreshToken(Authentication authentication) {
        String refreshToken = redisService.getValues("RT: " + authentication.getName());
        if (!StringUtils.hasText(refreshToken)) {
            throw new RuntimeException("로그아웃된 사용자입니다.");
        }
    }

    private void validateRefreshTokenOwner(Authentication authentication, TokenRequestDto tokenRequestDto) {
        if (!redisService.getValues("RT: " + authentication.getName()).equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }
}
