package com.cactro.github_integration.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateIssueResponse {
  private String url;
}
