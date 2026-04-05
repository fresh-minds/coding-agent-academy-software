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
