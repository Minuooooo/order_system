package jpabook.jpashop.config.oauth.client;

import jpabook.jpashop.config.oauth.common.OAuthApiClient;
import jpabook.jpashop.config.oauth.common.OAuthInfoResponse;
import jpabook.jpashop.config.oauth.common.OAuthLoginParams;
import jpabook.jpashop.config.oauth.common.OAuthProvider;
import jpabook.jpashop.config.oauth.dto.KakaoTokens;
import jpabook.jpashop.config.oauth.info.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private final RestTemplate restTemplate;
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;
    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {

        String url = authUrl + "/oauth/token";  // Token Provider Url

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);  // Query Parameter 형식으로 변환

        MultiValueMap<String, String> body = params.makeBody();  // Token 요청에 필요한 Query Parameter
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);  // POST 요청

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOAuthInfo(String accessToken) {

        String url = apiUrl + "/v2/user/me";  // Info Provider Url

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}
