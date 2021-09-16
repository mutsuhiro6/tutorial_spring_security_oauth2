package com.github.mutsuhiro6.tutorial_spring_security_oauth2.handler;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialOAuth2User;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.repository.TutorialUserPrincipalInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @author Mutsuhiro Iwamoto
 *
 */
@Slf4j
@Component
public class TutorialOAuth2SuccessHandler {

  private final TutorialUserPrincipalInMemoryRepository repository;

  public TutorialOAuth2SuccessHandler(TutorialUserPrincipalInMemoryRepository repository) {
    this.repository = repository;
  }

  /**
   * Executed when an authentication is done successfully.
   * Register my own defined User Principal to in memory database.
   * If already registered, do nothing.
   * @param event
   */
  @EventListener
  public void listen(InteractiveAuthenticationSuccessEvent event) {
    log.info(String.valueOf(event));
    TutorialOAuth2User user = (TutorialOAuth2User) event.getAuthentication().getPrincipal();
    repository.insertOrIgnore(user.getTutorialUserPrincipal());
  }
}
