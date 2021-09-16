package com.github.mutsuhiro6.tutorial_spring_security_oauth2.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mutsuhiro Iwamoto
 *
 * Implemetation of OAuth2User to include my own User Principal information.
 */
public class TutorialOAuth2User implements OAuth2User {

  private TutorialUserPrincipal tutorialUserPrincipal;

  private UUID userId;

  private String provider;

  private String providerId;

  private final OAuth2User oAuth2User;

  public TutorialOAuth2User(UUID userId, String provider, String providerId, OAuth2User oAuth2User) {
    this.userId = userId;
    this.provider = provider;
    this.providerId = providerId;
    this.oAuth2User = oAuth2User;
  }

  public TutorialUserPrincipal getTutorialUserPrincipal() {
    return new TutorialUserPrincipal(
            userId,
            provider,
            providerId,
            oAuth2User.getAttribute("name"),
            oAuth2User.getAttribute("email")
    );
  }

  @Override
  public <A> A getAttribute(String name) {
    return oAuth2User.getAttribute(name);
  }

  @Override
  public Map<String, Object> getAttributes() {
    return oAuth2User.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return oAuth2User.getAuthorities();
  }

  @Override
  public String getName() {
    return oAuth2User.getName();
  }
}
