# Exercise 10 - Feature Implementation (Plan-First)

## Objective
Use a Coding Agent to implement a new feature using a plan-first workflow before coding.

## Starting Branch
- `exercise/10-feature-implementation-plan-first` (branched from `main`).

## Scope
- Participant first produces an implementation plan (decision-complete).
- Then participant executes implementation with agent support.
- Preserve existing contracts unless feature explicitly extends them.

## Expected Outcomes
- Clear plan artifact exists before code changes.
- Feature is delivered with tests and concise docs updates.
- Participant demonstrates disciplined agent usage from planning to verification.

## Hints
- Ask agent to separate discovery, decisions, and execution steps.
- Lock acceptance criteria before implementation.
- Ask for risk notes and rollback considerations for non-trivial changes.

## Done Criteria
- Plan-first flow is visible in branch history/docs.
- Feature behavior is test-covered and works locally.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/10-feature-implementation-plan-first`.
- README includes:
  - Feature summary and implementation highlights.
  - Literal subagent prompt used.
  - Short rationale.
