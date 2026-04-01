# Exercise 05 - Agent-Assisted Code Review

## Objective
Use a Coding Agent to review a prepared PR-style diff and identify bugs, risks, regressions, and test gaps.

## Starting Branch
- `exercise/05-code-review` (branched from `main`).

## Scope
- Branch contains an intentional in-branch diff/scenario to review.
- Participant asks agent for findings-first review output.
- Focus on correctness and maintainability over stylistic nits.

## Expected Outcomes
- Review output prioritizes findings by severity.
- Clear references to impacted files/lines and risk explanation.
- Follow-up fixes or recommendations are actionable.

## Hints
- Ask agent to separate findings from summary.
- Request missing test scenarios explicitly.
- Confirm backward compatibility and contract impact.

## Done Criteria
- At least one substantive review finding or explicit "no findings" with residual risk notes.
- Participant captures review quality, not only prose quantity.
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/05-code-review`.
- README includes:
  - Final findings and resulting changes (if any).
  - Literal subagent prompt used.
  - Short rationale.
