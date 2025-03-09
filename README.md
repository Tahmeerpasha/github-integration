# GitHub Integration Service

## Overview
This project is a **Spring Boot** application that integrates with the GitHub API to perform various actions like fetching user details, retrieving repository information, and creating issues in repositories.

## Features
- Retrieve authenticated GitHub user information.
- Fetch details of a specific repository.
- Create a new issue in a GitHub repository.

## Technologies Used
- **Spring Boot** (REST API development)
- **Spring Web** (for HTTP client communication)
- **Lombok** (to reduce boilerplate code)
- **Jackson** (for JSON parsing)
- **RestTemplate** (for making API calls)
- **Maven** (build automation tool)

## Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Maven
- A GitHub personal access token (PAT) with the necessary permissions

## Configuration
### 1. Set Up GitHub Credentials
Create an `application.yml` file under `src/main/resources/` and add the following:
```yaml
github:
  base-url: "https://api.github.com"
  username: "your-github-username"
  token: "your-github-personal-access-token"
```

Alternatively, you can set environment variables:
```sh
export GITHUB_BASE_URL="https://api.github.com"
export GITHUB_USERNAME="your-github-username"
export GITHUB_TOKEN="your-github-personal-access-token"
```

## Running the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/github-integration-service.git
   cd github-integration-service
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. The application will start at `http://localhost:8080`.

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/github/user` | Fetch authenticated GitHub user info |
| GET | `/api/github/repo/{repoName}` | Fetch details of a repository |
| POST | `/api/github/repo/{repoName}/issue` | Create a new issue in a repository |

## Using Swagger UI
The API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```
You can test the endpoints directly from the Swagger UI.

## Exception Handling
- Custom exception `GitHubServiceException` is thrown when an API request fails.
- Proper error logging is implemented using **SLF4J and Lombok's @Slf4j**.

## Future Enhancements
- Support for listing issues in a repository
- OAuth-based authentication
- Improved error handling with detailed GitHub API response parsing

## License
This project is licensed under the **MIT License**. Feel free to use and modify it as needed!

---
🎯 **Happy Coding!** 🚀

