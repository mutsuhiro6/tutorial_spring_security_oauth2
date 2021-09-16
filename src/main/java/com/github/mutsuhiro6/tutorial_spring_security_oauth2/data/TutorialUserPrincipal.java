package com.github.mutsuhiro6.tutorial_spring_security_oauth2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Mutsuhiro Iwamoto
 *
 * My own User Principal
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorialUserPrincipal {

  private UUID userId;

  private String provider;

  private String providerId;

  private String username;

  private String email;
}
