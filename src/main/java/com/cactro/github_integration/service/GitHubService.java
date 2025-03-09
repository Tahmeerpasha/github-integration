package com.cactro.github_integration.service;

import com.cactro.github_integration.configuration.GitHubConfig;
import com.cactro.github_integration.dto.CreateIssueResponse;
import com.cactro.github_integration.exception.GitHubServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Service class for interacting with GitHub API.
 */
@Service
@Slf4j
public class GitHubService {

  @Autowired
  private GitHubConfig gitHubConfig;

  @Autowired
  private RestTemplate restTemplate;

  /**
   * Fetches authenticated GitHub user information.
   *
   * @return {@link JsonNode} containing user details.
   * @throws GitHubServiceException if an error occurs during API call.
   */
  public JsonNode getUserInfo() {
    try {
      log.info("Fetching user information from GitHub");
      String url = gitHubConfig.getBaseUrl() + "/user";

      ResponseEntity<JsonNode> response = restTemplate
        .exchange(url, HttpMethod.GET, null, JsonNode.class);
      log.debug("Successfully retrieved user info");
      return response.getBody();
    } catch (Exception e) {
      log.error("Error fetching user information: {}", e.getMessage());
      throw new GitHubServiceException("Failed to fetch user information from GitHub", e);
    }
  }

  /**
   * Fetches information about a specific GitHub repository.
   *
   * @param repoName The name of the repository.
   * @return {@link JsonNode} containing repository details.
   * @throws GitHubServiceException if an error occurs during API call.
   */
  public JsonNode getRepoInfo(String repoName) {
    try {
      log.info("Fetching repository information for repo: {}", repoName);
      String url = gitHubConfig.getBaseUrl() + "/repos/{owner}/{repo}";

      ResponseEntity<JsonNode> response = restTemplate
        .exchange(
          url,
          HttpMethod.GET,
          null,
          JsonNode.class,
          gitHubConfig.getUsername(),
          repoName
        );
      log.debug("Successfully retrieved repo info for: {}", repoName);
      return response.getBody();
    } catch (Exception e) {
      log.error("Error fetching repository information for {}: {}", repoName, e.getMessage());
      throw new GitHubServiceException("Failed to fetch repository information from GitHub", e);
    }
  }

  /**
   * Creates a new issue in a specified GitHub repository.
   *
   * @param repoName The name of the repository.
   * @param title    The title of the issue.
   * @param body     The body/content of the issue.
   * @return {@link CreateIssueResponse} containing the issue URL.
   * @throws GitHubServiceException if an error occurs during API call.
   */
  public CreateIssueResponse createIssue(String repoName, String title, String body) {
    try {
      log.info("Creating new issue in repository: {}", repoName);

      String url = gitHubConfig.getBaseUrl() + "/repos/{owner}/{repo}/issues";

      Map<String, String> requestBody = new HashMap<>();
      requestBody.put("title", title);
      requestBody.put("body", body);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

      ResponseEntity<JsonNode> response = restTemplate
        .exchange(url, HttpMethod.POST, entity, JsonNode.class, gitHubConfig.getUsername(), repoName);

      log.debug("Successfully created issue in repository: {}", repoName);
      String issueUrl = String.valueOf(Objects.requireNonNull(response.getBody()).get("html_url"));
      log.info("Issue created successfully: {}", issueUrl);
      return CreateIssueResponse.builder().url(issueUrl).build();
    } catch (Exception e) {
      log.error("Error creating issue in repository {}: {}", repoName, e.getMessage());
      throw new GitHubServiceException("Failed to create issue in GitHub repository", e);
    }
  }
}
