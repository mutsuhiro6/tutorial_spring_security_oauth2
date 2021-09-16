package com.github.mutsuhiro6.tutorial_spring_security_oauth2.repository;

import com.github.mutsuhiro6.tutorial_spring_security_oauth2.data.TutorialUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mutsuhiro Iwamoto
 */
@Slf4j
@Component
public class TutorialUserPrincipalInMemoryRepository {

  private List<TutorialUserPrincipal> db = new ArrayList<>();

  public Optional<TutorialUserPrincipal> findByKey(String provider, String providerId) {
    log.info("Finding a TutorialUserPrincipal record by keys.");
    return db.stream()
            .filter(p -> provider.equals(p.getProvider()) && providerId.equals(p.getProviderId()))
            .findAny();
  }

  public void insert(TutorialUserPrincipal tutorialUserPrincipal) {
    log.info("Inserting a TutorialUserPrincipal record.");
    db.add(tutorialUserPrincipal);
  }

  public void insertOrIgnore(TutorialUserPrincipal tutorialUserPrincipal) {
    if (findByKey(tutorialUserPrincipal.getProvider(), tutorialUserPrincipal.getProviderId()).isEmpty()) {
      insert(tutorialUserPrincipal);
    }
    else {
      // ignore
      log.info("Ignore because the TutorialUSerPrincipal record already exists");
    }
  }

  public List<TutorialUserPrincipal> findAll() {
    return this.db;
  }
}
