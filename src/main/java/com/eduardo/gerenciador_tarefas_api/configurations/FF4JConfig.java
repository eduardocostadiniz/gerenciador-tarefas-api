package com.eduardo.gerenciador_tarefas_api.configurations;

import org.ff4j.FF4j;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.store.InMemoryFeatureStore;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4JConfig {

  private static final String FF4J_FEATURE_CONTROLLER = "is-feature-controller-active";
  private static final String FF4J_SCHEDULING_ENABLED = "is-scheduling_enabled";

  @Bean
  public FF4j ff4J() {
    FF4j ff4j = new FF4j();

    ff4j.setFeatureStore(new InMemoryFeatureStore());
    ff4j.setPropertiesStore(new InMemoryPropertyStore());
    ff4j.setEventRepository(new InMemoryEventRepository());
    ff4j.audit(true);

    // TODO: mover para controller quando tirar o InMemoryStore
    ff4j.createFeature(FF4J_FEATURE_CONTROLLER);
    ff4j.enable(FF4J_FEATURE_CONTROLLER);
    ff4j.createFeature(FF4J_SCHEDULING_ENABLED);
    ff4j.disable(FF4J_SCHEDULING_ENABLED);

    return ff4j;
  }

  @Bean
  @ConditionalOnMissingBean
  public FF4jDispatcherServlet defineFF4jServlet(FF4j ff4j) {
    FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
    ff4jConsoleServlet.setFf4j(ff4j);
    return ff4jConsoleServlet;
  }

  @Bean
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ServletRegistrationBean registerFF4jServlet(FF4jDispatcherServlet ff4jDispatcherServlet) {
    return new ServletRegistrationBean(ff4jDispatcherServlet, "/ff4j-web-console/*");
  }
}
