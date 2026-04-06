# Exercise 07: Repetitive Tasks

Full project context lives on [`main`](../../tree/main).

Use a Coding Agent to audit the README files across the exercise and solution branches in this repository.

Audit rules:

- Every `exercise/*` branch README should contain a description of the exercise.
- Every `solution/*` branch README should contain the original exercise, a brief explanation of the solution, and the literal prompt that was fed to the Coding Agent.

Your task is to inspect the relevant branches and produce a report that lists any branches whose README content is missing or incorrect.

Reference solution: [`solution/07-repetitive-tasks`](../../tree/solution/07-repetitive-tasks)

## Solution

This solution uses a subagent to perform a read-only audit of the root `README.md` files across the local `exercise/*` and `solution/*` branches. The subagent was instructed to apply the rules from this exercise README only, report missing or incorrect content, and not fix any README files.

Audit report:

- `solution/02-javadoc`: README includes the original exercise and a literal prompt, but it does not clearly include a brief explanation of the solution itself; the added `Rationale` explains the prompt choice rather than summarizing what the solution changed.
- `solution/03-ide-code-completion`: README is missing the original exercise and missing the literal prompt fed to the Coding Agent.
- `solution/04-tests`: README includes the original exercise and a literal prompt, but it does not include a brief explanation of the solution.
- `solution/06-refactoring`: README is missing the original exercise.
- `solution/07-repetitive-tasks`: README contains only the original exercise; it is missing both a brief explanation of the solution and the literal prompt fed to the Coding Agent.

Summary on missing original exercise:

At least `solution/03-ide-code-completion` and `solution/06-refactoring` appear to be missing the original exercise from their README content.

Literal subagent prompt used:

```text
Audit the README files across the exercise and solution branches in this repository. Base your task definition only on the following exercise README text, and do not use planning files or assume any extra requirements beyond it.

Rules:
- Every `exercise/*` branch README should contain a description of the exercise.
- Every `solution/*` branch README should contain the original exercise, a brief explanation of the solution, and the literal prompt that was fed to the Coding Agent.

Task:
- Inspect the root `README.md` in each local `exercise/*` and `solution/*` branch.
- Report only branches whose README content is missing or incorrect.
- Do not modify any files.
- Keep the report concise and branch-specific.
```
