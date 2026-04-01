# Exercise 06 - Refactoring with Behavioral Safety

## Objective
Use a Coding Agent to refactor selected modules for clarity and maintainability while preserving behavior.

## Starting Branch
- `exercise/06-refactoring` (branched from `main`).

## Scope
- Improve structure of target modules (for example: decomposition, naming, duplication reduction).
- Preserve existing API contracts unless explicitly documented.
- Keep refactor incremental and reviewable.

## Expected Outcomes
- Codebase readability and maintainability improve.
- Behavioral parity is demonstrated via tests or checks.
- Refactor rationale is clear and minimal-risk.

## Hints
- Ask agent for a "safe refactor plan" before code edits.
- Split large refactors into small commits logically.
- Run tests frequently to guard behavior.

## Done Criteria
- Refactor merged with no intentional feature changes.
- Tests/checks pass after refactor.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/06-refactoring`.
- README includes:
  - What was refactored and why.
  - Literal subagent prompt used.
  - Short rationale.
