package jpabook.jpashop.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.member.dto.sign.LoginRequestDto;
import jpabook.jpashop.domain.member.dto.sign.SignUpRequestDto;
import jpabook.jpashop.domain.member.dto.sign.TokenRequestDto;
import jpabook.jpashop.domain.member.dto.sign.ValidateSignUpRequestDto;
import jpabook.jpashop.domain.member.entity.Member;
import jpabook.jpashop.domain.member.service.AuthService;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static jpabook.jpashop.response.Response.*;
import static jpabook.jpashop.response.SuccessMessage.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth API Document")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @Operation(summary = "Validate duplicate API", description = "put your validate duplicate info")
    @ResponseStatus(OK)
    @PostMapping("/validate-duplicate")
    public Response validateDuplicateUsername(@Valid @RequestBody ValidateSignUpRequestDto validateSignUpRequestDto) {
        authService.validateDuplicate(validateSignUpRequestDto);
        return success(SUCCESS_TO_VALIDATE_DUPLICATE);
    }

    @Operation(summary = "Sign up API", description = "put your sign up info.")
    @ResponseStatus(CREATED)
    @PostMapping("/sign-up")
    public Response signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        Member member = authService.signUp(signUpRequestDto);
        return success(SUCCESS_TO_SIGN_UP);
    }

    @Operation(summary = "Sign in API", description = "put your sign in info.")
    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    public Response signIn(@Valid @RequestBody LoginRequestDto req) {
        return success(SUCCESS_TO_SIGN_IN, authService.signIn(req));
    }

    @Operation(summary = "Logout API", description = "this is logout")
    @PostMapping("/logout")
    @ResponseStatus(OK)
    public Response logout() {
        Member currentMember = memberService.getCurrentMember();
        authService.logout(currentMember);
        return success(SUCCESS_TO_SIGN_OUT);
    }

    @Operation(summary = "Reissue API", description = "put your token info which including access token and refresh token.")
    @ResponseStatus(OK)
    @PostMapping("/reissue")
    public Response reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return success(SUCCESS_TO_REISSUE, authService.reissue(tokenRequestDto));
    }
}
