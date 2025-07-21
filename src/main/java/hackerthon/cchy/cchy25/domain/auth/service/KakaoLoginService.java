package hackerthon.cchy.cchy25.domain.auth.service;

import hackerthon.cchy.cchy25.domain.auth.dto.SocialType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoLoginService implements OauthService {

    private final String tokenIssueUrl = "https://kauth.kakao.com/oauth/token";
    private final String clientId = "22eab5145311a4edbcc3e061de0b8a28";
    private final String redirectUrl = "http://localhost:8080/kakao-oauth.html";

    @Override
    public SocialType getLoginType() {
        return SocialType.KAKAO;
    }

    @Override
    public String getToken(String code) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.ACCEPT_CHARSET, "UTF-8");

        var body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
        body.add("code",code);

        var entity = new HttpEntity<>(
                body,
                headers
        );

        var response = restTemplate.postForEntity(
                        tokenIssueUrl,
                        entity,
                        Map.class);

        return response.getBody().get("access_token").toString();
    }

    @Override
    public String getSocialId(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<Map> response = new RestTemplate().postForEntity(
                "https://kapi.kakao.com/v2/user/me",
                new HttpEntity<>(headers),
                Map.class
        );

        return response.getBody().get("id").toString();
    }
}
