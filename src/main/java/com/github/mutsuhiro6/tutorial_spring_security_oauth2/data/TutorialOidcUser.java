package com.github.mutsuhiro6.tutorial_spring_security_oauth2.data;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;
import java.util.UUID;

/**
 * @author Mutsuhiro Iwamoto
 * <p>
 * This class has to be created to use TutorialOAuth2User.
 */
public class TutorialOidcUser extends TutorialOAuth2User implements OidcUser {

  private final OidcUser oidcUser;

  public TutorialOidcUser(UUID userId, String provider, String providerId, OidcUser oidcUser) {
    super(userId, provider, providerId, oidcUser);
    this.oidcUser = oidcUser;
  }

  @Override
  public Map<String, Object> getClaims() {
    return oidcUser.getClaims();
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return oidcUser.getUserInfo();
  }

  @Override
  public OidcIdToken getIdToken() {
    return oidcUser.getIdToken();
  }
}
