package jpabook.jpashop.response;

import lombok.Getter;

@Getter
public class SuccessMessage {
    public static final String SUCCESS = "요청에 성공했습니다";
    public static final String SUCCESS_TO_FIND_EMAIL = "이메일을 찾는데 성공했습니다.";
    public static final String SUCCESS_TO_VALIDATE_DUPLICATE = "중복 확인에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_UP = "회원가입에 성공했습니다.";
    public static final String SUCCESS_TO_SIGN_IN = "로그인에 성공했습니다.";
    public static final String SUCCESS_TO_LOGOUT = "로그아웃에 성공했습니다.";
    public static final String SUCCESS_TO_REISSUE = "토큰 재발급에 성공했습니다.";
    public static final String SUCCESS_TO_GET_MEMBER = "회원 정보를 조회하는데 성공했습니다.";
    public static final String SUCCESS_TO_EDIT_MEMBER = "회원 프로필 정보를 변경하는데 성공했습니다.";
    public static final String SUCCESS_TO_DELETE_MEMBER = "회원을 삭제하는데 성공했습니다.";
    public static final String SUCCESS_TO_CHANGE_MEMBER_PROFILE_IMAGE = "회원 프로필 이미지를 변경하는데 성공했습니다.";
    public static final String SUCCESS_TO_CREATE_ORDER_INFO = "주문에 필요한 정보를 생성하는데 성공했습니다.";
    public static final String SUCCESS_TO_GET_ORDER_INFOS = "주문에 필요한 정보를 가져오는데 성공했습니다.";
    public static final String SUCCESS_TO_EDIT_ORDER_INFO = "주문에 필요한 정보를 수정하는데 성공했습니다.";
    public static final String SUCCESS_TO_ORDER_COMPLETELY = "주문하는데 성공했습니다.";
    public static final String SUCCESS_TO_GET_ITEM_INFO = "물품 정보 목록을 조회하는데 성공했습니다.";
    public static final String SUCCESS_TO_PUT_ITEM = "물품을 장바구니에 담는데 성공했습니다.";
    public static final String SUCCESS_TO_GET_ORDER_ITEM_INFOS = "장바구니에 담은 물품을 조회하는데 성공했습니다.";
    public static final String SUCCESS_TO_EDIT_ORDER_ITEM_INFO = "장바구니에 담은 물품 정보를 수정하는데 성공했습니다.";
    public static final String SUCCESS_TO_CANCEL_ORDER_ITEM = "장바구니에 담은 물품을 취소하는데 성공했습니다.";
    public static final String SUCCESS_TO_CREATE_CHAT_ROOM = "채팅방을 생성하는데 성공했습니다.";
    public static final String SUCCESS_TO_GET_CHAT_ROOM_INFOS = "채팅방 정보를 가져오는데 성공했습니다.";
    public static final String SUCCESS_TO_EDIT_CHAT_ROOM_INFO = "채팅방 정보를 수정하는데 성공했습니다.";
    public static final String SUCCESS_TO_DELETE_CHAT_ROOM = "채팅방을 삭제하는데 성공했습니다.";
}