package hackerthon.cchy.cchy25.domain.auth.service;

import hackerthon.cchy.cchy25.domain.auth.dto.SocialType;

public interface OauthService {
    SocialType getLoginType();
    String getToken(String code);
    String getSocialId(String token);
}
