# Exercise 10: Feature Implementation (Plan-First)

Use this branch to practice a plan-first feature implementation workflow with a Coding Agent.

For the full project context, baseline application, and general repository instructions, see the [`main` branch](https://github.com/<your-fork-or-origin>/coding-agent-academy-software/tree/main).

When you are done, compare your result with the matching solution branch: `solution/10-feature-implementation-plan-first`.

This branch contains the assignment only. It does not contain the implementation of the feature described below.

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
