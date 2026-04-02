# Exercise 01 - Debugging Out-of-Order Reviews

For full project setup and baseline context, see the `main` branch.

## Assignment

There is a bug in one of these services. See the bug report below.
It's your task to use a Coding Agent to find and fix the bug.

The solution, including an example prompt, can be found in the `solution/01-debugging` branch.

### Summary
The persisted "last review" for a pastry is occasionally incorrect after multiple quick submissions.

### Expected
The most recently submitted review for a pastry should be stored as that pastry's last review.

### Actual
An older review can end up stored as the pastry's last review.

### Impact
Users may receive stale or incorrect recommendation context based on outdated review data.

## Solution

### Prompt Used
```text
You are implementing Exercise 01 on branch `solution/01-debugging` in `/Users/rdebokx/workspace/coding-agent-academy-software`.

Ownership and constraints:
- You own bugfix implementation and any needed tests in Java service modules (`ratings-api` and/or `matcher-api`).
- Read only the exercise context from `README.md` and the current codebase.
- Keep architecture, REST/OpenAPI contracts, and endpoint signatures unchanged.
- Avoid unrelated refactors or formatting-only churn.

Task:
1) Diagnose and implement a minimal targeted bugfix for the reported behavior.
2) Add or update deterministic tests that demonstrate the bug is fixed.
3) Run relevant tests and report commands + outcomes.

Deliverables:
- Root cause summary.
- Files changed.
- Why the fix works.
- Verification steps/results.
```

### Change Performed
The bug was caused by publishing rating events to Kafka without a per-pastry message key. That allowed events for the same pastry to be distributed across partitions, so ingestion order could differ from submission order and stale "last review" values could be materialized.

The fix updates the publisher to send rating events with `pastryId` as the Kafka key, which preserves ordering for events of the same pastry within Kafka partition semantics.

Changed files:
- `ratings-api/src/main/java/com/pastryvibe/ratings/service/RatingPublisherService.java`
- `ratings-api/src/test/java/com/pastryvibe/ratings/service/RatingPublisherServiceTest.java`
- `ratings-api/pom.xml`

### Verification
- `mvn -q -pl ratings-api test` passed.
- `mvn -q -pl ratings-api,matcher-api test` passed.
- Diff safety checks passed:
  - no changes in `openapi/`
  - no REST endpoint/contract changes
