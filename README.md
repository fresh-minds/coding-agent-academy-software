# Exercise 09 Solution: CI Pipeline Creation

This branch contains the completed solution for Exercise 09.

## Result

A minimal GitHub Actions workflow now lives in [`.github/workflows/ci.yml`](.github/workflows/ci.yml). It runs on `push` and `pull_request`, uses `ubuntu-latest`, installs Temurin JDK 21, caches Maven dependencies, and executes `mvn verify` from the repository root.

## Solution

The workflow was created with a single build job to keep the training repo stable and easy to understand. No extra gates or unrelated build changes were added.

### Literal Subagent Prompt

```text
You are implementing the exercise described in the current branch README.

Ownership:
- `/Users/rdebokx/workspace/coding-agent-academy-software/.github/workflows/ci.yml`
- `/Users/rdebokx/workspace/coding-agent-academy-software/README.md`

You are not alone in the codebase. Do not revert edits you did not make, and adjust to any existing changes you find.

Task:
1. Read the current root README and follow the assignment as written there.
2. Create a minimal GitHub Actions CI workflow in `.github/workflows/ci.yml`.
3. Keep it minimal and stable for a training repository.
4. Trigger on `pull_request` and `push`.
5. Use an Ubuntu runner.
6. Install Temurin JDK 21.
7. Cache Maven dependencies.
8. Run `mvn verify` from the repository root.
9. Update the root README so it reflects the completed solution rather than the exercise starter state. Keep it concise and practical.
10. Do not add unrelated refactors or extra quality gates.

Before editing, inspect the repo to confirm the root Maven build and current branch expectations. After editing, summarize exactly what you changed and mention any verification you performed.
```

### Rationale

The prompt was scoped to the exact training goal: produce the first practical CI pipeline without introducing extra complexity. That keeps the generated solution aligned with the exercise and easy for participants to compare against.
