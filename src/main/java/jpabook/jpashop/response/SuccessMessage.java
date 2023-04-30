package jpabook.jpashop.response;

import lombok.Getter;

@Getter
public class SuccessMessage {
    public static final String SUCCESS = "요청에 성공했습니다";
    public static final String SUCCESS_TO_VALIDATE_DUPLICATE = "중복 확인에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_UP = "회원가입에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_IN = "로그인에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_OUT = "로그아웃에 성공했습니다.";
    public static final String SUCCESS_TO_REISSUE = "토큰 재발급에 성공했습니다.";
}