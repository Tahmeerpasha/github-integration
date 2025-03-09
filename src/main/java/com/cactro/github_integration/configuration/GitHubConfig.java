package com.cactro.github_integration.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@Getter
public class GitHubConfig {
  @Value("${github.api.base-url}")
  private String baseUrl;

  @Value("${github.api.token}")
  private String token;

  @Value("${github.username}")
  private String username;

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
      request.getHeaders().set("Authorization", "Bearer " + token);
      request.getHeaders().set("Accept", "application/vnd.github.v3+json");
      return execution.execute(request, body);
    }));

    return restTemplate;
  }
}
