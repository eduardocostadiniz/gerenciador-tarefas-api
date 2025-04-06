package com.eduardo.gerenciador_tarefas_api.controllers;

import com.eduardo.gerenciador_tarefas_api.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {

  @Autowired private CacheService cacheService;

  @DeleteMapping("/{cacheName}")
  public ResponseEntity<String> getCacheByName(@PathVariable final String cacheName) {
    cacheService.clearCacheByName(cacheName);
    return ResponseEntity.ok("OK");
  }
}
