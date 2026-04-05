# Exercise 04 - Add Unit and Integration Tests

This branch is for practicing Coding Agent-assisted test creation on the existing Java codebase. Your goal is to improve confidence in the current behavior by adding focused, readable, and deterministic tests without redesigning the application.

## Full Context

For the full repository baseline and application context, see the [`main` branch](../../tree/main).

## Assignment

Use a Coding Agent to add test coverage for existing behavior with a balanced unit-test and integration-test strategy.

- Add unit tests for matching and scoring logic, including important edge cases.
- Add service-level integration tests using Testcontainers for Kafka and MySQL where relevant.
- Keep the current architecture intact; do not redesign the services.

## Expected Outcome

- Key business paths and edge cases are covered.
- Integration tests validate contract-level behavior without introducing full two-service end-to-end coupling.
- Test code is readable, deterministic, and reusable where appropriate.
- Coverage meaningfully increases in the core modules.

## Done Criteria

- Added tests run locally and pass.
- The most important matching, ranking, tie-breaking, and invalid-input scenarios are covered.
- The resulting test suite is understandable for future contributors.

## Solution Branch

See the matching solution on [`solution/04-tests`](../../tree/solution/04-tests).

## Solution

```text
Implement Exercise 04 on branch `solution/04-tests` in your forked workspace.

You are not alone in the codebase. Do not revert edits made by others, and adjust your work to accommodate any concurrent changes.

Ownership:
- You own test additions under `matcher-api/src/test/...`
- You own test-related dependency changes in `matcher-api/pom.xml`
- Do not modify production code unless it is strictly necessary to make the tests possible, and if you think that is needed, explain why before doing it in your final summary.

Goal:
Add meaningful unit and integration test coverage for existing behavior, matching the exercise brief:
- Add unit tests for matching/scoring logic and edge cases.
- Add service-level integration tests using Testcontainers (Kafka + MySQL where relevant).
- Do not redesign architecture.

Repo context:
- Ranking/validation logic lives in `matcher-api/src/main/java/com/pastryvibe/matcher/service/SuggestionService.java`
- Kafka/MySQL ingestion behavior lives in `matcher-api/src/main/java/com/pastryvibe/matcher/service/RatingIngestionService.java`
- Existing matcher app config is in `matcher-api/src/main/resources/application.yml`

What to implement:
- Add test dependencies needed for JUnit 5, Spring Boot tests, Mockito if needed, Spring Kafka test support, and Testcontainers for MySQL and Kafka.
- Add focused unit tests for `SuggestionService` that cover at least:
  - rejecting empty flavor requests
  - rejecting more than 3 flavors
  - only returning pastries with at least one overlapping flavor
  - ranking order by average rating descending, then overlapping matched flavor count descending, then pastry name ascending
  - sane handling when stats are absent
- Add service-level integration test(s) for matcher ingestion using Spring Boot + Testcontainers that verify at least:
  - a published rating event is consumed and persisted
  - pastry stats are refreshed with rating count / average / last review fields
  - duplicate event IDs are ignored
- Keep fixtures explicit and readable.
- Prefer assertions on behavior, not implementation details.

Verification:
- Run the most relevant matcher-api test command(s) before finishing if your environment allows.
- In your final response, include:
  - changed files
  - tests/commands you ran and whether they passed
  - any limitations or follow-up risks

Do not edit the README; I will handle that after verification.
```
