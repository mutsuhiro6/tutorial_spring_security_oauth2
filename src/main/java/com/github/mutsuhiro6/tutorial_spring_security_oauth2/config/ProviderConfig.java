package com.github.mutsuhiro6.tutorial_spring_security_oauth2.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mutsuhiro Iwamoto
 */
@Data
@Configuration
public class ProviderConfig {

  @Value("${spring.security.oauth2.client.registration.github.clientId}")
  private String githubClientId;
  @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
  private String githubClientSecret;

  @Value("${spring.security.oauth2.client.registration.google.clientId}")
  private String googleClientId;
  @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
  private String googleClientSecret;

}
