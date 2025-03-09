package com.cactro.github_integration.exception;

public class GitHubServiceException extends RuntimeException {
  public GitHubServiceException(String message) {
    super(message);
  }

  public GitHubServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
