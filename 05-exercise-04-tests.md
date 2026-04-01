# Exercise 04 - Add Unit and Integration Tests

## Objective
Use a Coding Agent to add test coverage for existing behavior with a balanced unit-test and integration-test strategy.

## Starting Branch
- `exercise/04-tests` (branched from `main`).

## Scope
- Add unit tests for matching/scoring logic and edge cases.
- Add service-level integration tests using Testcontainers (Kafka + MySQL where relevant).
- Do not redesign architecture.

## Expected Outcomes
- Key business paths and edge cases are covered.
- Integration tests validate contract-level behavior without full two-service E2E coupling.
- Test code is readable and deterministic.

## Hints
- Start from high-risk logic: ranking, tie-breaking, invalid inputs.
- Keep fixture setup reusable and explicit.
- Prefer assertions on behavior, not internal implementation details.

## Done Criteria
- Added tests run locally and pass.
- Coverage meaningfully increases in core modules.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/04-tests`.
- README includes:
  - Test strategy summary.
  - Literal subagent prompt used.
  - Short rationale.
