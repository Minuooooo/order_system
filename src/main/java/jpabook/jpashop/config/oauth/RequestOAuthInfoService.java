package jpabook.jpashop.config.oauth;


import jpabook.jpashop.config.oauth.common.OAuthApiClient;
import jpabook.jpashop.config.oauth.common.OAuthInfoResponse;
import jpabook.jpashop.config.oauth.common.OAuthLoginParams;
import jpabook.jpashop.config.oauth.common.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {

    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {  // clients: [KakaoApiClient, NaverApiClient]
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())  // Function.identity: KakaoApiClient or NaverApiClient
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOAuthInfo(accessToken);
    }
}
