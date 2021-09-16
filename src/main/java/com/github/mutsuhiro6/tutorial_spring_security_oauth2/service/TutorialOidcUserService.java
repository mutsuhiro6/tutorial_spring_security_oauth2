package com.github.mutsuhiro6.tutorial_spring_security_oauth2.service;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialOidcUser;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialUserPrincipal;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.repository.TutorialUserPrincipalInMemoryRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Mutsuhiro Iwamoto
 * <p>
 * Service class for the authentication with OpenID connect (OIDC).
 * http://openid-foundation-japan.github.io/openid-connect-core-1_0.ja.html
 */
@Service
public class TutorialOidcUserService extends OidcUserService {

  private final TutorialUserPrincipalInMemoryRepository repository;

  public TutorialOidcUserService(TutorialUserPrincipalInMemoryRepository repository) {
    super();
    this.repository = repository;
  }

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);

    String provider = userRequest.getClientRegistration().getRegistrationId();

    // sub (subject): Unique key in the issuers (e.g. google, facebook).
    String subject = oidcUser.getSubject();

    Optional<TutorialUserPrincipal> principal = repository.findByKey(provider, subject);

    // Identifier in my app.
    UUID userId;
    if (principal.isPresent()) {
      userId = principal.get().getUserId();
    } else {
      userId = UUID.randomUUID();
    }
    return new TutorialOidcUser(userId, provider, subject, oidcUser);
  }
}
