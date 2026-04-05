# Exercise 10: Feature Implementation (Plan-First)

Use this branch to practice a plan-first feature implementation workflow with a Coding Agent.

For the full project context, baseline application, and general repository instructions, see the [`main` branch](https://github.com/<your-fork-or-origin>/coding-agent-academy-software/tree/main).

When you are done, compare your result with the matching solution branch: `solution/10-feature-implementation-plan-first`.

This branch contains a sample solution for the feature described below.

## Assignment

Implement a new endpoint in `matcher-api` that returns the "Best pastry of all time".

The endpoint should determine the best pastry using the ratings data already stored in the matcher service database. The result should reflect the pastry that has been rated best so far.

Before coding, first work with your Coding Agent to create a decision-complete implementation plan. Only then move on to implementation and verification.

TIP: instruct your Coding Agent to spin up a subagent afterwards to review the implementation that it generated.

## API Contract

### Request

`GET /api/v1/pastries/best-rated`

### Response

`200 OK`

```json
{
  "pastryId": "STROOPWAFEL",
  "pastryName": "Stroopwafel",
  "averageRating": 4.7,
  "ratingCount": 9,
  "lastReviewDescription": "Great texture and balanced sweetness."
}
```

`404 Not Found`

Return `404` when no pastry has been rated yet.

## Functional Requirements

- The new endpoint must live in `matcher-api`.
- The result must be based on ratings data stored in the matcher service database.
- The endpoint must return the best-rated pastry so far.
- The response must include `pastryId`, `pastryName`, `averageRating`, `ratingCount`, and `lastReviewDescription`.
- Include unit tests for the new endpoint.

## Solution

Feature summary: this solution adds a matcher-side endpoint that returns the current best-rated pastry using persisted matcher data and returns `404` when no rated pastry exists.

Implementation highlights: a dedicated controller and service were added for `GET /api/v1/pastries/best-rated`, the service ranks pastries from persisted `pastry_stats` data joined with pastry display names, and focused service/controller tests prove the endpoint contract and not-found behavior.

Literal prompt used:

```text
"You are the implementation worker for this repository. You are not alone in the codebase. Do not revert edits made by others, and adjust your work to accommodate changes already present.

Ownership: implement only the Exercise 10 solution for the new matcher-api feature and the root README solution section in your forked workspace.

Task context:
- Repository: /Users/rdebokx/workspace/coding-agent-academy-software
- Current branch in the main workspace is solution/10-feature-implementation-plan-first.
- The exercise branch README asks participants to implement a new matcher-api endpoint for the \"Best pastry of all time\".
- The endpoint contract is GET /api/v1/pastries/best-rated
- 200 response fields: pastryId, pastryName, averageRating, ratingCount, lastReviewDescription
- 404 when no pastry has been rated yet
- The result must be based on ratings data stored in the matcher service database
- Participants are expected to implement tests

Your constraints:
- First, inspect the repo and provide a concise implementation plan only. Do not make code changes yet.
- Your plan must confirm your understanding of the exercise, identify the matcher-api files you expect to touch, explain how you will derive the best pastry from existing persisted data, and list the tests you will add.
- Do not edit files until I explicitly authorize implementation.
- When you reply, include a short section named \"Understanding\" and a short section named \"Plan\"."
```

Rationale: the prompt constrains the work to the matcher solution, forces an explicit readback plan before coding, and makes the expected data source, API contract, and test scope unambiguous.
