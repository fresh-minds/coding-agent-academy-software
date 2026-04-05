# Exercise 08 - Skills and Playbooks

This branch contains Exercise 08 of the Java Coding Agent Academy. For the full repository context and baseline project, start from [`main`](https://github.com/rdebokx/coding-agent-academy-software/tree/main).

When you are done with this exercise, compare your result with the matching solution branch: [`solution/08-skills-playbooks`](https://github.com/rdebokx/coding-agent-academy-software/tree/solution/08-skills-playbooks).

## Objective

Design a reusable Coding Agent skill or playbook that creates a pull request for the current branch in a safe, repeatable way.

This exercise is about defining the skill and its instructions. Do not actually create a pull request as part of the exercise, and do not use the skill to open a real PR while working on this branch.

## Assignment

Create a skill or playbook that guides a Coding Agent through the full PR creation flow for the current Git branch.

The skill must require the agent to:

1. Check whether there are uncommitted changes in the working tree.
2. If uncommitted changes exist, stop and instruct the user to commit them first.
3. Check whether there are local commits that have not been pushed.
4. If unpushed commits exist, stop and instruct the user to push them first.
5. Determine the repository default branch automatically.
6. Create a pull request with `gh`, using the authenticated GitHub user as the assignee.
7. Use a conventional-commit-style PR title.
8. Generate a PR description that summarizes the branch-specific diff between branch creation and the current branch state.
9. Exclude merge commits from the default branch into the current branch when generating that summary.
10. Detect `CODEOWNERS` if present and use the relevant owners as reviewers when possible.
11. Open the created pull request in a new browser window or tab.

## Constraints

- Keep the skill reusable across repositories where possible.
- Prefer checks that fail fast before any PR creation command is attempted.
- Make the instructions explicit enough that another consultant can use the skill without reinterpreting the workflow.
- If reviewer selection from `CODEOWNERS` cannot be fully automated, document a best-effort approach and the limitations clearly.

## Validation

Document how you validated the skill design without actually creating a real pull request. For example, you can describe the commands, dry-run reasoning, or manual verification steps you would use to confirm that:

- the skill blocks when the worktree is dirty;
- the skill blocks when commits are not pushed;
- the default branch detection works;
- the generated PR summary focuses on branch-specific changes rather than merge noise;
- `CODEOWNERS` reviewer selection is handled when applicable.

## Done Criteria

- A reusable skill or playbook is documented for PR creation.
- The skill prevents PR creation when the working tree is not clean.
- The skill prevents PR creation when the branch has unpushed commits.
- The skill determines the default branch automatically.
- The skill defines how to create a PR with `gh` and assign the current authenticated user.
- The skill requires a conventional-commit-style PR title.
- The skill requires a PR description based on the branch diff, excluding merge commits from the default branch.
- The skill handles `CODEOWNERS`-based reviewer selection when applicable.
- The exercise output explains how the skill was validated without opening a real PR.

## Solution

Use the example below as a quality reference, not as a requirement to match word for word. A strong solution is concise, stops on failed prerequisites, names the critical commands or checks, and makes the PR summary branch-specific instead of polluted by merge commits from the default branch.

You can verify that a learner-created skill is good enough by checking these points:

- The metadata makes it clear when the skill should trigger.
- The instructions stop immediately when the worktree is dirty.
- The instructions stop immediately when the branch has unpushed commits.
- The workflow explains how the default branch is detected instead of hardcoding `main`.
- The PR creation step includes `gh`, a conventional-commit-style title, assignee selection, reviewer handling, and browser opening.
- The PR summary guidance is based on branch-specific history and explicitly excludes merge noise from the default branch.
- The skill stays reusable and does not contain repo-specific clutter that is not needed for the workflow.

Example `SKILL.md` contents:

```md
---
name: pr-creator
description: Create a GitHub pull request safely from the current branch. Use when the user wants to open a PR, set assignees/reviewers, or generate a branch-specific PR description without merge noise.
---

# PR Creator

Create a pull request only when the repository state is ready.

## Rules

- Stop immediately if the working tree is dirty.
- Stop immediately if the branch has unpushed commits.
- Do not modify files.
- Do not continue past a failed prerequisite.
- Keep the workflow repository-agnostic where possible.

## Workflow

1. Detect the repository default branch automatically.
   - Prefer `gh repo view --json defaultBranchRef --jq '.defaultBranchRef.name'`.
   - Fall back to the remote's default branch if needed.

2. Check for uncommitted changes.
   - Use `git status --porcelain`.
   - If anything is present, instruct the user to commit first and stop.

3. Check for unpushed commits.
   - Compare the current branch with its upstream.
   - If the branch is ahead of its upstream, instruct the user to push first and stop.

4. Determine the branch base for the PR summary.
   - Use the merge base with the default branch, or the branch point if it is more reliable.
   - Build the summary from branch-specific work only.
   - Exclude merge commits from the default branch into the current branch.
   - A practical pattern is `git log --no-merges --first-parent <base>..HEAD`.

5. Inspect `CODEOWNERS` if present.
   - Derive reviewer handles from the files matched by the branch changes.
   - Request those reviewers when possible.
   - If exact mapping is ambiguous, use best effort and note the limitation.

6. Create the PR with `gh`.
   - Use a conventional-commit-style title.
   - Set the authenticated GitHub user as assignee.
   - Include the branch-specific summary in the PR body.
   - Open the created PR in the browser.

## PR Body

Include:
- a short summary of the branch's purpose,
- a concise bullet list of meaningful commits or changes,
- notes about key files or behavior changes,
- reviewers derived from `CODEOWNERS` if applicable.

Do not include merge commits from the default branch in the summary.
```
