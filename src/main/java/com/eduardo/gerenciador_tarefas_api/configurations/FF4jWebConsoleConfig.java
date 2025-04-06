package com.eduardo.gerenciador_tarefas_api.configurations;

import org.ff4j.FF4j;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AutoConfigureAfter(FF4JConfig.class)
public class FF4jWebConsoleConfig extends SpringBootServletInitializer {

  private static final String FF4J_REMEMBER_ME_COOKIE_NAME = "ff4j-remember-me-cookie";

  @Value("${ff4j.web-console.username}")
  private String ff4jUsername;

  @Value("${ff4j.web-console.password}")
  private String ff4jPassword;

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

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/projects/**")
                    .permitAll()
                    .requestMatchers("/users/**")
                    .permitAll()
                    .requestMatchers("/tasks/**")
                    .permitAll()
                    .requestMatchers("/health")
                    .permitAll()
                    .requestMatchers("/features/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(form -> form.usernameParameter(ff4jUsername).passwordParameter(ff4jPassword))
        .rememberMe(rememberMe -> rememberMe.rememberMeCookieName(FF4J_REMEMBER_ME_COOKIE_NAME));
    return httpSecurity.build();
  }

  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
    final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    inMemoryUserDetailsManager.createUser(
        User.withUsername(ff4jUsername)
            .password(bCryptPasswordEncoder.encode(ff4jPassword))
            .roles("USER", "ADMIN")
            .build());
    return inMemoryUserDetailsManager;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
