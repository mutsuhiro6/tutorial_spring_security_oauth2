package com.github.mutsuhiro6.tutorial_spring_security_oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author Mutsuhiro Iwamoto
 */
@SpringBootApplication
public class TutorialSpringSecurityOauth2Application {

  public static void main(String[] args) {
    SpringApplication.run(TutorialSpringSecurityOauth2Application.class, args);
  }

}
