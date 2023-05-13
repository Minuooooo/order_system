package jpabook.jpashop.config.oauth.common;

import jpabook.jpashop.domain.user.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
