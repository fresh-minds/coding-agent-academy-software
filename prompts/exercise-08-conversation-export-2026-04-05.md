# Conversation Export

## User
You are currently in the plans branch from a repo that hold exercises for Java-exports to learn how to work with Coding Agents. Read 01-overall-plan.md and 09-exercise-08-skills-playbooks.md. Let's instruct the user to create a skill for creating a PR. This skill should do the following:
- Check that there are no uncommitted changes. If there are uncommitted changes, propose the ocmmit them first.
- Confirm that there are no unpushed changes. If there are unpushed changes, propose to push them.
- Deteremine the default branch of the repo
- Create a PR using the ghub command, with the current user as assignee. Use conventional commit format in the title and include a description that contains a summary of the diff between the creation of the branch and the current state of the branch, exclude merge commits from the default branch into the current branch. If there are CODEOWNER files, use these users as reviewers for the PR.
- Open the PR in a new browser. 

Provide me a plan:
- create a branch for the exercise based on the main branch
- Clear the Readme contents and fill it with a description of the exercise as I mentioned above.
- Do not actually create this skill or create a PR.

## Agent
I’m grounding this in the repo first by reading the overall plan and the exercise spec you referenced, then I’ll map your requested PR-skill exercise onto the existing structure without making any repo changes.

## Agent
I’ve got the source material. Next I’m checking the current README shape and the branch layout so the plan can name the exact exercise branch and README rewrite without inventing repo conventions.

## Agent
The repo confirms there isn’t an `exercise/08-skills-playbooks` branch yet, and the current README is just a placeholder. I’m reading the adjacent repetitive-tasks exercise now so the proposed branch and README changes fit the sequence cleanly.

## Agent
<proposed_plan>
# Exercise 08 PR-Creation Skill Plan

## Summary
Create a new exercise branch from `main` named `exercise/08-skills-playbooks`, and replace the placeholder [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) with an exercise brief that asks the learner to design a reusable Coding Agent skill for opening a pull request safely and consistently.

This stays at the exercise-definition level only. Do not implement the skill, do not run it, and do not create any pull request as part of this work.

## Key Changes
- Create `exercise/08-skills-playbooks` from `main`, not from the current `plans` branch.
- Replace the contents of [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) entirely with an exercise README that includes:
  - A short intro explaining this is Exercise 08 in the Coding Agent Academy sequence.
  - A link back to `main` for full repo context.
  - A link to the matching future solution branch `solution/08-skills-playbooks`.
  - The assignment: define a skill/playbook that creates a PR for the current branch.
  - A clear statement that the learner must only create the skill definition and supporting instructions, not actually open a PR while doing the exercise.
- Describe the required skill behavior in the README exactly enough for implementation:
  - Verify there are no uncommitted changes; if there are, instruct the user to commit first.
  - Verify there are no unpushed commits; if there are, instruct the user to push first.
  - Determine the repository default branch automatically.
  - Create a PR with `gh`, assigning the current GitHub user to the PR.
  - Use a conventional-commit-style PR title.
  - Generate a PR body summarizing the diff from branch creation to current HEAD.
  - Exclude merge commits from the default branch into the feature branch when generating that summary.
  - If `CODEOWNERS` is present, derive reviewers from it and request them.
  - Open the created PR in a browser.
- Add README acceptance criteria so the learner knows when the exercise is complete:
  - The skill is reusable and repo-agnostic where possible.
  - The skill blocks PR creation when the working tree or remote state is not ready.
  - The generated PR content is based on branch-specific work, not merge noise.
  - CODEOWNERS-based reviewer selection is handled when applicable.
  - The README documents how the learner validated the skill without actually creating a real PR during the exercise.

## Public Interfaces / Outputs
- No code or runtime API changes.
- The only intended repo change is the branch plus the rewritten exercise [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md).
- The README should act as the full exercise contract for the learner.

## Test Plan
- Confirm the new branch is based on `main`.
- Review the README and verify it matches the Exercise 08 topic and repository branch naming convention.
- Verify the README includes the mandatory exercise-branch links to `main` and `solution/08-skills-playbooks`.
- Verify the README explicitly says not to create the actual skill output or a real PR as part of this planning task.
- Verify every required PR-skill behavior from your prompt is represented in the assignment and done criteria.

## Assumptions
- `exercise/08-skills-playbooks` and `solution/08-skills-playbooks` are the correct branch names, consistent with the existing numbering scheme.
- The exercise continues the repo convention that branch READMEs are self-contained assignment briefs.
- “Current user as assignee” means the authenticated `gh` user running the skill.
- Reviewer selection from `CODEOWNERS` is best-effort and should be framed that way in the exercise, since matching changed paths to owners can vary by implementation.
</proposed_plan>

## User
PLEASE IMPLEMENT THIS PLAN:
# Exercise 08 PR-Creation Skill Plan

## Summary
Create a new exercise branch from `main` named `exercise/08-skills-playbooks`, and replace the placeholder [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) with an exercise brief that asks the learner to design a reusable Coding Agent skill for opening a pull request safely and consistently.

This stays at the exercise-definition level only. Do not implement the skill, do not run it, and do not create any pull request as part of this work.

## Key Changes
- Create `exercise/08-skills-playbooks` from `main`, not from the current `plans` branch.
- Replace the contents of [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) entirely with an exercise README that includes:
  - A short intro explaining this is Exercise 08 in the Coding Agent Academy sequence.
  - A link back to `main` for full repo context.
  - A link to the matching future solution branch `solution/08-skills-playbooks`.
  - The assignment: define a skill/playbook that creates a PR for the current branch.
  - A clear statement that the learner must only create the skill definition and supporting instructions, not actually open a PR while doing the exercise.
- Describe the required skill behavior in the README exactly enough for implementation:
  - Verify there are no uncommitted changes; if there are, instruct the user to commit first.
  - Verify there are no unpushed commits; if there are, instruct the user to push first.
  - Determine the repository default branch automatically.
  - Create a PR with `gh`, assigning the current GitHub user to the PR.
  - Use a conventional-commit-style PR title.
  - Generate a PR body summarizing the diff from branch creation to current HEAD.
  - Exclude merge commits from the default branch into the feature branch when generating that summary.
  - If `CODEOWNERS` is present, derive reviewers from it and request them.
  - Open the created PR in a browser.
- Add README acceptance criteria so the learner knows when the exercise is complete:
  - The skill is reusable and repo-agnostic where possible.
  - The skill blocks PR creation when the working tree or remote state is not ready.
  - The generated PR content is based on branch-specific work, not merge noise.
  - CODEOWNERS-based reviewer selection is handled when applicable.
  - The README documents how the learner validated the skill without actually creating a real PR during the exercise.

## Public Interfaces / Outputs
- No code or runtime API changes.
- The only intended repo change is the branch plus the rewritten exercise [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md).
- The README should act as the full exercise contract for the learner.

## Test Plan
- Confirm the new branch is based on `main`.
- Review the README and verify it matches the Exercise 08 topic and repository branch naming convention.
- Verify the README includes the mandatory exercise-branch links to `main` and `solution/08-skills-playbooks`.
- Verify the README explicitly says not to create the actual skill output or a real PR as part of this planning task.
- Verify every required PR-skill behavior from your prompt is represented in the assignment and done criteria.

## Assumptions
- `exercise/08-skills-playbooks` and `solution/08-skills-playbooks` are the correct branch names, consistent with the existing numbering scheme.
- The exercise continues the repo convention that branch READMEs are self-contained assignment briefs.
- “Current user as assignee” means the authenticated `gh` user running the skill.
- Reviewer selection from `CODEOWNERS` is best-effort and should be framed that way in the exercise, since matching changed paths to owners can vary by implementation.

## Agent
I’m creating the exercise branch from `main`, then I’ll rewrite [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) into the Exercise 08 brief and verify the result against the plan.

## Agent
The worktree is clean, so branch creation is safe. Next I’m switching to `main`, creating `exercise/08-skills-playbooks`, and then I’ll replace the README contents in one edit.

## Agent
I’m editing [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) now. The new content will be a self-contained exercise brief with the PR-skill requirements, done criteria, and explicit “do not actually create a PR” guardrail.

## Agent
The patch missed because the file content on `main` differs from what was on `plans`. I’m reading the current [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) from the new branch and then I’ll replace it against the actual contents.

## Agent
I’ve got the actual `main` README now. I’m replacing it wholesale so this branch becomes a focused exercise brief rather than inheriting the baseline repository intro.

## Agent
The README rewrite is in place. I’m verifying branch ancestry, the final README content, and the working tree so I can report exactly what changed and whether it matches the exercise contract.

## Agent
Created branch `exercise/08-skills-playbooks` from `main` and replaced [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) with the Exercise 08 brief. The new README links to `main` and `solution/08-skills-playbooks`, defines the PR-creation skill assignment, includes the required checks and `gh` workflow details, and explicitly says not to create a real PR as part of the exercise.

Verification: the branch is based on `main` and the worktree now only shows the intended README change on `exercise/08-skills-playbooks`. I did not implement the skill and did not create any PR.

## User
Great, I've pushed these changes. Next, create a new branch based on this branch, meant for the solution and adherring to the naming convention for solution branches. Spin up a sugagent that can Create Skills, but instruct it to only provide the contents of the SKILL.md basd on this exercise. Make sure it doesn't actually create this skill. If the returned SKILL.md contents look good, add it in a "Solution" section to the readme, describing how the user can verify that the skill it created is of good enough quality based on this SKILL.md contents as example. You should inclue the full contents of this example SKILL.MD contents in the Readme in a preformatted block

## Agent
I’m using the `skill-creator` skill for this turn because the task is to draft a skill example, not implement one. First I’ll branch from `exercise/08-skills-playbooks`, read the skill instructions, then I’ll delegate only the SKILL.md drafting to a subagent and integrate the result into the README if it holds up.

## Agent
The branch is clean and still on `exercise/08-skills-playbooks`. I’m creating `solution/08-skills-playbooks` from here, then I’ll hand the SKILL.md drafting to a worker with a strict “text only, no files” boundary.

## Agent
I’m delegating the example skill draft now. The worker owns no files and must return only candidate `SKILL.md` content that satisfies the exercise constraints, so I can review it before touching the README.

## Agent
While the worker drafts the skill text, I’m shaping the README update locally so the solution section will explain both what “good” looks like and how to evaluate it against the example.

## Agent
The draft is usable: it covers the required guardrails and keeps the skill concise. I’m adding it verbatim to the solution README, along with explicit review criteria so learners can judge whether their own skill is at the same quality bar.

## Agent
The patch context didn’t match, so I’m reading the current README as it exists on the solution branch and then I’ll append the solution block against the exact file contents.

## Agent
The current README on this branch only contains the exercise intro and assignment list, so I’m replacing it with a complete solution-branch version that preserves the exercise brief and adds the solution guidance and example skill body.

## Agent
The README is updated. I’m doing a quick verification pass on the new branch state and the final README content, then I’ll close out the worker since its draft has been fully integrated.

## Agent
Created `solution/08-skills-playbooks` from `exercise/08-skills-playbooks` and updated [`README.md`](/Users/rdebokx/workspace/coding-agent-academy-software/README.md) to include a `Solution` section. That section now gives review criteria for judging a learner’s skill and embeds the full example `SKILL.md` contents in a fenced block, based on a subagent-generated draft that I reviewed before integrating.

I did not create the actual skill. The branch is currently `solution/08-skills-playbooks`, and the only working-tree change is the README update.

## User
[$export-conversation](/Users/rdebokx/.codex/skills/export-conversation/SKILL.md) add an export to the `prompts` folder

## User
[$export-conversation](/Users/rdebokx/.codex/skills/export-conversation/SKILL.md) add an export to the `prompts` folder

## User
<skill>
<name>export-conversation</name>
<path>/Users/rdebokx/.codex/skills/export-conversation/SKILL.md</path>
---
name: export-conversation
description: Export the current conversation to a Markdown file containing only the literal message text exchanged between the user and the agent. Use when the user asks to export a conversation, save the current thread, write the chat to Markdown, or preserve the transcript with clear user/agent separation.
---

# Export Conversation

Export the current thread to a Markdown file that preserves only the literal conversation content.

## Workflow

1. Read the current thread carefully and collect only the user messages and agent responses that are part of the conversation being exported.
2. Exclude analysis, hidden chain-of-thought, tool call payloads, tool outputs, timestamps, token counts, IDs, and any metadata unless the user explicitly asks for them.
3. Write the output as Markdown with a clear label for each turn, using a consistent structure such as `## User` and `## Agent` for each message block.
4. Preserve the literal visible message text. Do not summarize, rewrite, normalize, interpret, or redact unless the user explicitly asks for that.
5. Keep the original ordering of the conversation.
6. If the user specifies an output path or filename, use it. Otherwise choose a sensible Markdown filename in the current working directory.

## Output Rules

- Store only literal message content from the conversation.
- Make role boundaries obvious.
- Keep Markdown simple and readable.
- Do not add commentary before or after the transcript unless the user asks for it.
- Do not invent missing messages.

## Default Format

Use this pattern unless the user requests a different layout:

```md
# Conversation Export

## User
<literal user message>

## Agent
<literal agent response>
```

Repeat the `User` and `Agent` sections in order for the full conversation.

## Filename Guidance

Prefer descriptive names such as:

- `conversation-export.md`
- `thread-export.md`
- a user-provided filename

## Completion

After writing the file, report the final path.
</skill>
