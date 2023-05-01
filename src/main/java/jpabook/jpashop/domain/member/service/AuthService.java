package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.config.jwt.JwtProvider;
import jpabook.jpashop.config.redis.RedisService;
import jpabook.jpashop.domain.member.dto.sign.*;
import jpabook.jpashop.domain.member.entity.Address;
import jpabook.jpashop.domain.member.entity.Authority;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.exception.situation.LoginFailureException;
import jpabook.jpashop.exception.situation.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final JwtProvider jwtProvider;

    public void duplicateValidate(ValidateSignUpRequestDto validateSignUpRequestDto) {
        validateSignUpInfo(validateSignUpRequestDto);
    }

    public Member signUp(SignUpRequestDto req) {
        Member member = createSignupFormOfUser(req);
        memberRepository.save(member);
        return member;
    }

    public TokenResponseDto signIn(LoginRequestDto req) {
        Member member = validateExistsByUsername(req);
        validatePassword(req, member);

        Authentication authentication = getUserAuthentication(req);
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

    private void validateSignUpInfo(ValidateSignUpRequestDto validateSignUpRequestDto) {
        if (memberRepository.existsByUsername(validateSignUpRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException(validateSignUpRequestDto.getUsername());
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
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .address(getAddress(req))
                .profileImageUrl("basic")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Member validateExistsByUsername(LoginRequestDto req) {
        return memberRepository.findByUsername(req.getUsername()).orElseThrow(() -> {
            throw new LoginFailureException();
        });
    }

    private void validatePassword(LoginRequestDto loginRequestDto, Member member) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private Authentication getUserAuthentication(LoginRequestDto req) {
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();
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
