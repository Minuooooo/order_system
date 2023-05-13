package jpabook.jpashop.config.oauth.common;

import jpabook.jpashop.domain.user.entity.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
