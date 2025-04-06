package com.eduardo.gerenciador_tarefas_api.services;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

  @Autowired private CacheManager cacheManager;

  public void clearCacheByName(final String cacheName) {
    log.info("Limpando cache: {}", cacheName);
    Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
  }
}
