package com.cactro.github_integration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateIssueRequest {
  private String title;
  private String body;
}
