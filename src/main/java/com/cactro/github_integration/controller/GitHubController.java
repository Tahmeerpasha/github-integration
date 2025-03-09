package com.cactro.github_integration.controller;

import com.cactro.github_integration.dto.CreateIssueRequest;
import com.cactro.github_integration.dto.CreateIssueResponse;
import com.cactro.github_integration.service.GitHubService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/github")
@Tag(name = "GitHub API", description = "GitHub API Integration to fetch user and repository data and create issues.")
public class GitHubController {

  @Autowired
  private GitHubService gitHubService;

  @Operation(
    summary = "Get GitHub user info",
    description = "Fetches GitHub user details such as followers, following count, and repositories.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched user info",
        content = @Content(mediaType = "application/json"))
    }
  )
  @GetMapping
  public JsonNode getGitHubInfo() {
    return gitHubService.getUserInfo();
  }

  @Operation(
    summary = "Get repository info",
    description = "Retrieves information about a specific GitHub repository.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully fetched repo info",
        content = @Content(mediaType = "application/json"))
    }
  )
  @GetMapping("/{repoName}")
  public JsonNode getRepo(@PathVariable String repoName) {
    return gitHubService.getRepoInfo(repoName);
  }

  @Operation(
    summary = "Create an issue in a repository",
    description = "Creates a new issue in the specified GitHub repository.",
    responses = {
      @ApiResponse(responseCode = "201", description = "Issue created successfully",
        content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = CreateIssueResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
    }
  )
  @PostMapping("/{repoName}/issues")
  public CreateIssueResponse createIssue(
    @PathVariable String repoName,
    @RequestBody CreateIssueRequest request
  ) {
    return gitHubService.createIssue(repoName, request.getTitle(), request.getBody());
  }
}
