package jpabook.jpashop.response;

import lombok.Getter;

@Getter
public class SuccessMessage {
    public static final String SUCCESS = "요청에 성공했습니다";
    public static final String SUCCESS_TO_FIND_EMAIL = "이메일을 찾는데 성공했습니다.";
    public static final String SUCCESS_TO_VALIDATE_DUPLICATE = "중복 확인에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_UP = "회원가입에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_IN = "로그인에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_OUT = "로그아웃에 성공했습니다.";
    public static final String SUCCESS_TO_REISSUE = "토큰 재발급에 성공했습니다.";
    public static final String SUCCESS_TO_EDIT_MEMBER = "회원 프로필 정보를 변경하는데 성공했습니다.";
    public static final String SUCCESS_TO_DELETE_MEMBER = "회원을 삭제하는데 성공했습니다.";
    public static final String SUCCESS_TO_CHANGE_MEMBER_PROFILE_IMAGE = "회원 프로필 이미지를 변경하는데 성공했습니다.";

}