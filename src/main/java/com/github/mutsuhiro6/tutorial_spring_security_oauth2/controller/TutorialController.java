package com.github.mutsuhiro6.tutorial_spring_security_oauth2.controller;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialUserPrincipal;
import com.github.mutsuhiro6.tutorial_spring_security_oauth2.repository.TutorialUserPrincipalInMemoryRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mutsuhiro Iwamoto
 */
@Controller
public class TutorialController {

  private final TutorialUserPrincipalInMemoryRepository repository;

  public TutorialController(TutorialUserPrincipalInMemoryRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/")
  public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
    if (null != principal) {
      model.addAttribute("username", principal.getAttribute("name"));
      model.addAttribute("map", principal.getAttributes());
    }
    return "index";
  }

  @GetMapping("/error")
  public String error(HttpServletRequest request) {
    String message = (String) request.getSession().getAttribute("error.message");
    request.getSession().removeAttribute("error.message");
    return message;
  }

  @GetMapping("/registeredUsers")
  public String registeredUsers(Model model) {
    List<TutorialUserPrincipal> registeredUsers = this.repository.findAll();
    model.addAttribute("users", registeredUsers);
    return "registeredUsers";
  }
}
