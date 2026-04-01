# Exercise 01 - Debugging Out-of-Order Reviews

## Objective
Use a Coding Agent to investigate and fix an out-of-order data bug where a pastry's persisted "last review" is sometimes incorrect.

## Starting Branch
- `exercise/01-debugging` (branched from `main`).

## Context to Include in Branch README (Bug Report Style)
- **Summary:** Last review for a pastry is occasionally wrong after multiple quick submissions.
- **Expected:** The latest submitted review for a pastry should be stored as that pastry's last review.
- **Actual:** An older review can become the stored last review.
- **Impact:** Users receive stale/incorrect recommendation context.
- **Hints (non-spoiler):**
  - Review producer-side Kafka publish behavior.
  - Compare partitioning assumptions with per-pastry ordering needs.
  - Inspect consumer update logic for "last review" writes.

## Scope
- Diagnose likely cause.
- Implement a fix that improves per-pastry ordering guarantees.
- Keep current architecture and endpoint contracts unchanged.

## Expected Outcomes
- Bug cause is documented clearly in commit/readme notes.
- Code change addresses ordering issue for same pastry events.
- Existing flows still run locally with Docker Compose.

## Hints
- Trace event ordering expectations from producer to consumer write path.
- Validate whether per-pastry ordering is actually guaranteed by current publish strategy.
- Keep the first fix minimal and targeted before broader hardening changes.

## Recommended Agent Workflow
- Ask agent to map event flow end to end.
- Ask for a minimal fix with rationale and risk notes.
- Ask for a quick verification approach (manual and/or test-level).

## Done Criteria
- Root cause identified as design/config issue (not random runtime fluke).
- Fix committed and explained.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/01-debugging`.
- README includes:
  - What changed and why.
  - Literal subagent prompt used.
  - Short rationale for prompt wording and constraints.
