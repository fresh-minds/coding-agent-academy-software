# Exercise 05 - Agent-Assisted Code Review

This branch is for practicing Coding Agent-assisted code review on a prepared feature diff in the Java training application.

## Full Context

For the full repository baseline and application context, see the [`main` branch](../../tree/main).

## Assignment

Review this branch as if it were a pull request.

- A new `matcher-api` endpoint was added to return the best rated pastry of all time.
- The API contract was updated, and both the IntelliJ and Postman request collections were extended.
- The implementation is intended to work functionally, but the diff should still be reviewed.

Review the changes in this branch as if:
- it were a PR from a medior Java developer in your team, or;
- as if this was code you've written (with or without Coding Agent). Although the changes in this branch may contain some flaws that you would usually not push ;) 

## Solution Branch

See the matching solution on [`solution/05-code-review`](../../tree/solution/05-code-review).

## Solution

Literal prompt used for the Coding Agent review:

```text
Review the current branch as a PR against `main`.

Context:
- Repository: `/Users/rdebokx/workspace/coding-agent-academy-software`
- Current branch: `solution/05-code-review`
- The exercise branch introduced a new matcher-api endpoint to return the best rated pastry of all time.
- Your job is not to fix anything. Produce a code review.

Instructions:
- Use a findings-first review format.
- Prioritize bugs, behavioral regressions, contract mismatches, missing tests, and maintainability/performance risks that matter in practice.
- Keep stylistic nits out unless they hide a real engineering risk.
- Include file references and line numbers when possible.
- Order findings by severity.
- After the findings, add a very short residual-risk summary.
- If there are no findings, say so explicitly.

Important review angles for this diff:
- Does the implementation match the new OpenAPI contract and intended endpoint behavior?
- Is the winner selection logic safe and deterministic?
- Are there edge cases where the endpoint returns the wrong result or the wrong status?
- Are there obvious performance or data-access issues in the implementation?
- What tests are missing that should block approval?

Please inspect the actual diff from `main...HEAD` rather than only the README. Return only the review output.
```

## Sub-Agent Findings

These were the main findings produced by the sub-agent review:

1. The implementation in `BestRatedPastryService#getBestRatedPastry()` fetches all `pastry_stats`, fetches all pastries, joins them with a nested loop, and sorts the full list in memory before taking one result. That is an avoidable performance and data-access issue for an endpoint that only needs a single winner.
2. The new `best-rated` endpoint has no automated test coverage. The `404` path, tie-breakers on average rating, rating count, and pastry name, and the response mapping are all unverified, which is a real regression risk for a new public API.

Residual risk noted by the sub-agent: the endpoint shape itself looked aligned with the contract, but correctness still depends on manual reasoning because the new behavior is untested.
