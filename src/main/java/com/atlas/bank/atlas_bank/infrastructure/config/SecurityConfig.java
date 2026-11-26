package com.atlas.bank.atlas_bank.infrastructure.config;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers(HttpMethod.POST, "/api/v1/accounts")
          .hasRole("ADMIN")
          .requestMatchers(HttpMethod.GET, "/api/v1/accounts")
          .hasRole("ADMIN")
          .requestMatchers(HttpMethod.GET, "/api/v1/accounts/{id}")
          .hasAnyRole("ADMIN", "USER")
          .requestMatchers(HttpMethod.POST, "/api/v1/transactions/transfer")
          .hasAnyRole("ADMIN", "USER")
          .requestMatchers(HttpMethod.GET, "/api/v1/transactions/{id}/transactions")
          .hasAnyRole("ADMIN", "USER")
          .requestMatchers(HttpMethod.POST, "/api/v1/ai/**")
          .permitAll()
          .requestMatchers("/h2-console/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .oauth2ResourceServer(oauth2 ->
        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
      )
      .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
      .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter =
      new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
      var realmAccess = jwt.getClaimAsMap("realm_access");
      if (realmAccess == null || realmAccess.get("roles") == null) {
        return List.of();
      }
      var roles = (List<String>) realmAccess.get("roles");
      return roles
        .stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    });

    return jwtAuthenticationConverter;
  }
}
