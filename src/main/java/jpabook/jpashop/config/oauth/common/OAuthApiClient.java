package jpabook.jpashop.config.oauth.common;

import jpabook.jpashop.domain.user.entity.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
