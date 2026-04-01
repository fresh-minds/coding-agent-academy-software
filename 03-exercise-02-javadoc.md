# Exercise 02 - Javadoc Quality Upgrade

## Objective
Use a Coding Agent to add and improve Javadoc for core domain/service APIs so intent, constraints, and behavior are clear for future maintainers.

## Starting Branch
- `exercise/02-javadoc` (branched from `main`).

## Scope
- Focus on public classes/methods most relevant to rating ingestion and matching logic.
- Document business meaning, important constraints, and edge behavior.
- Avoid noisy comments that restate code.

## Expected Outcomes
- Javadoc improves discoverability and onboarding speed.
- Critical contract points (input assumptions, scoring behavior, side effects) are documented.
- No functional behavior changes.

## Hints
- Prioritize API boundaries and non-obvious logic.
- Include `@param`, `@return`, and throws behavior where useful.
- Keep docs concise and accurate.

## Done Criteria
- Targeted high-value classes/methods now have clear Javadoc.
- Generated docs/readability spot-check passes.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/02-javadoc`.
- README includes:
  - Areas documented and why they were prioritized.
  - Literal subagent prompt used.
  - Short rationale.
