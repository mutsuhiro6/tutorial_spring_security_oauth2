package com.github.mutsuhiro6.tutorial_spring_security_oauth2.service;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialOAuth2User;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialUserPrincipal;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.repository.TutorialUserPrincipalInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Mutsuhiro Iwamoto
 */
@Slf4j
@Service
public class TutorialOAuth2UserService extends DefaultOAuth2UserService {

  private final TutorialUserPrincipalInMemoryRepository repository;

  public TutorialOAuth2UserService(TutorialUserPrincipalInMemoryRepository repository) {
    super();
    this.repository = repository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String providerId;
    String provider = userRequest.getClientRegistration().getRegistrationId();
    log.info(String.valueOf(oAuth2User.getAttributes()));
    switch (provider) {
      case "github":
        providerId = Integer.toString(oAuth2User.getAttribute("id"));
        break;
      default:
        throw new IllegalArgumentException("There is no such a provider:" + provider + ".");
    }

    Optional<TutorialUserPrincipal> tutorialUserPrincipal = repository.findByKey(provider, providerId);

    if (tutorialUserPrincipal.isPresent()) {
      TutorialUserPrincipal principal = tutorialUserPrincipal.get();
      return new TutorialOAuth2User(principal.getUserId(), provider, providerId, oAuth2User);
    } else {
      UUID userId = UUID.randomUUID();
      return new TutorialOAuth2User(userId, provider, providerId, oAuth2User);
    }
  }
}
