package com.eduardo.gerenciador_tarefas_api.controllers;

import com.eduardo.gerenciador_tarefas_api.exceptions.FeatureFlagNotEnabledException;
import com.eduardo.gerenciador_tarefas_api.models.FeatureFlagResponse;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/features")
public class FeatureFlagController {

  @Autowired private FF4j ff4j;

  @GetMapping("/{flagName}")
  public ResponseEntity<FeatureFlagResponse> getFeatureFlag(@PathVariable final String flagName) {
    if (!ff4j.check("is-feature-controller-active")) {
      throw new FeatureFlagNotEnabledException(HttpStatus.NOT_IMPLEMENTED.name());
    }

    final boolean isEnabled = ff4j.check(flagName);

    log.info("{} is enabled: {}", flagName, isEnabled);

    return ResponseEntity.ok(new FeatureFlagResponse(flagName, isEnabled));
  }
}
