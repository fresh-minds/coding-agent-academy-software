# Exercise 03 - IDE Code Completion with Agents

## Objective
Practice Coding Agent-assisted code completion in IntelliJ and VS Code by implementing a small method from TODO scaffolding.

## Starting Branch
- `exercise/03-ide-code-completion` (branched from `main`).

## Scope
- Provide a specific TODO method location in repo (to be created in implementation session).
- Participant uses in-IDE agent/completion to finish method safely and idiomatically.
- Exercise should show examples of intermediate completion suggestions and how to guide/refine them.

## Expected Outcomes
- Small method implemented correctly.
- Participant can compare raw completion output vs guided completion with explicit constraints.
- Compile/test checks remain green for affected area.

## Hints
- Prompt with exact method contract and edge cases.
- Ask for smallest correct implementation first, then improve naming/readability.
- Verify against existing style and domain enums.

## Done Criteria
- Method completed with clear logic and consistent style.
- README includes IntelliJ + VS Code usage notes (agent-agnostic phrasing).
- Exercise README links to matching solution branch.

## Solution Branch Expectations
- Branch: `solution/03-ide-code-completion`.
- README includes:
  - Final implementation summary.
  - Literal subagent prompt used.
  - Short rationale.
