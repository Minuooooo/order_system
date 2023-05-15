package jpabook.jpashop.config.oauth.common;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
