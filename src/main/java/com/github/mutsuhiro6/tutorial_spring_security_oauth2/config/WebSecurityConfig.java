package com.github.mutsuhiro6.tutorial_spring_security_oauth2.config;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.service.TutorialOAuth2UserService;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.service.TutorialOidcUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @author Mutsuhiro Iwamoto
 */
@Slf4j
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final TutorialOAuth2UserService oAuth2UserService;

  private final TutorialOidcUserService oidcUserService;

  private final ProviderConfig providerConfig;

  public WebSecurityConfig(TutorialOAuth2UserService oAuthUserService,
                           TutorialOidcUserService oidcUserService,
                           ProviderConfig providerConfig) {
    super();
    this.oAuth2UserService = oAuthUserService;
    this.oidcUserService = oidcUserService;
    this.providerConfig = providerConfig;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");
    http.authorizeRequests(a ->
                    a.antMatchers("/", "/error", "/registeredUsers")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
            .exceptionHandling(e ->
                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .oauth2Login(o -> o
                    .failureHandler((request, response, exception) -> {
                      request.getSession().setAttribute("error.message", exception.getMessage());
                      handler.onAuthenticationFailure(request, response, exception);
                      o.userInfoEndpoint().userService(oAuth2UserService);
                      o.userInfoEndpoint().oidcUserService(oidcUserService);
                    })

            );
    // http.userDetailsService()
    http.logout(l -> l.logoutSuccessUrl("/").permitAll());
    http.csrf();
  }


  /**
   * This is not needed if we only use pre-defined providers like github, google and facebook.
   * @return
   */
  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    log.info("Github client id " + providerConfig.getGithubClientId() + ".");
    return new InMemoryClientRegistrationRepository(
            CommonOAuth2Provider.GITHUB.getBuilder("github")
                    .clientId(providerConfig.getGithubClientId())
                    .clientSecret(providerConfig.getGithubClientSecret())
                    .build(),
            CommonOAuth2Provider.GOOGLE.getBuilder("google")
                    .clientId(providerConfig.getGoogleClientId())
                    .clientSecret(providerConfig.getGoogleClientSecret())
                    .build()
    );
  }

}
