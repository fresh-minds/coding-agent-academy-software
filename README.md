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
