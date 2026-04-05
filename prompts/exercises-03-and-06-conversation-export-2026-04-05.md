# Conversation Export: Exercises 03 and 06

Date: 2026-04-05

This export covers the work on:
- Exercise 03: IDE code completion
- Exercise 06: Refactoring

The final export request itself is intentionally excluded.

## Transcript

### User
You're currently in a repo that will hold a variety of exercises for Java experts to get familiar with different use cases for the userof coding agents. Read the 01-overall-plan.md and 04-exercise-03-ide-code-completion.md. You task is to create this exercise.
I want you to find a good candidate for adding a boilerplate function somewhere. Perhaps extracting some logic from an existing function would be a good candidate, but provide me with a suggestion first.

The next part of the plan is that you create an exercise branch for exercise-03 following the naming convention mentioned in the plan files, which will be based on the main branch. Remove all contents of the Readme file in this branch and store a description of the assignment in it. Keep it short an concise.

### Assistant
I read the plan files and inspected the repo. The best candidate I found was the duplicated `humanize(String pastryId)` logic in the matcher service layer, specifically in `RatingIngestionService` and `BaselineDataSeeder`.

I proposed using that duplication as the seed for exercise 03, by extracting it into a small helper such as `PastryNameFormatter`. That made the exercise a good fit for IDE-assisted code completion because it is small, realistic, and easy to complete safely with an in-IDE agent.

### User
PLEASE IMPLEMENT THIS PLAN:
# Exercise 03 Plan: IDE Code Completion via `PastryNameFormatter`

## Summary
Create `exercise/03-ide-code-completion` from `main`, not from `plans`.

Use the duplicated `humanize(String pastryId)` logic in [`matcher-api/src/main/java/com/pastryvibe/matcher/service/RatingIngestionService.java`](/Users/rdebokx/workspace/coding-agent-academy-software/matcher-api/src/main/java/com/pastryvibe/matcher/service/RatingIngestionService.java) and [`matcher-api/src/main/java/com/pastryvibe/matcher/service/BaselineDataSeeder.java`](/Users/rdebokx/workspace/coding-agent-academy-software/matcher-api/src/main/java/com/pastryvibe/matcher/service/BaselineDataSeeder.java) as the exercise seed. This is the best fit for exercise 03 because it is small, realistic, already present in the baseline, and easy to complete with IDE agent suggestions.

## Key Changes
- Create branch `exercise/03-ide-code-completion` from `main`.
- Add a new matcher-side helper class, package-private, in `com.pastryvibe.matcher.service`:
  - `PastryNameFormatter`
  - method: `String humanize(String pastryId)`
- Move the existing underscore-splitting/title-casing behavior into that helper in the solution design.
- In the exercise branch, leave `PastryNameFormatter#humanize` as the TODO completion target with a short inline TODO and a compilable placeholder return.
- Update `BaselineDataSeeder` and `RatingIngestionService` to call the helper instead of each keeping their own `humanize` method.
- Do not change public REST contracts, OpenAPI, persistence schema, or Kafka behavior.
- Replace the `README.md` contents on the exercise branch with a short assignment-only README that includes:
  - link to `main` for full context
  - one short assignment description
  - link to `solution/03-ide-code-completion`
  - brief IntelliJ/VS Code note telling participants to use in-IDE agent completion on `PastryNameFormatter#humanize`

## Test Plan
- Confirm the branch still builds from source after introducing the helper scaffold.
- Verify both updated callers compile against `PastryNameFormatter`.
- After the solution implementation, verify display names are humanized correctly for cases like `STROOPWAFEL`, `APPELFLAP`, and multi-word enum names with underscores.

## Assumptions
- `main` is the canonical source branch for this exercise.
- The exercise should stay intentionally small and should not introduce a new test suite beyond what already exists.
- The starting exercise branch should compile, so the TODO method will use a placeholder implementation rather than leaving the class uncompilable.
- Branch naming follows the locked convention from the plan: `exercise/03-ide-code-completion`.

### Assistant
I created `exercise/03-ide-code-completion` from `main`, added `PastryNameFormatter` with a TODO placeholder implementation, updated the two matcher services to use it, and replaced the branch README with a short exercise description.

I also verified the branch by running `mvn -q -DskipTests package`, which completed successfully.

### User
Great, I've pushed the changes. Now continue to implementing the solution in the solution branch.

### Assistant
I created `solution/03-ide-code-completion` from the current exercise branch, implemented `PastryNameFormatter#humanize`, and updated the README to the solution format with the implementation summary, literal prompt, and rationale.

I verified the solution branch with `mvn -q -DskipTests package`, which passed.

### User
make sure you branch from the current exercise branch

### Assistant
I verified the branch ancestry and confirmed that `solution/03-ide-code-completion` was branched from `exercise/03-ide-code-completion`.

At that point both branch refs still pointed at the same base commit because the solution work was not yet committed.

### User
Great, I've committed and pushed these changes as well. We've finished this exercise. Now I think of it, these code changes together in fact can be considered as a refactoring. I've switched back to the plans branch. Read 01-overall-plan.md and 07-exercise-06-refactoring.md , Create an exercise in which the user is asked to refactor this `humanize` method to make it DRY using a Coding Agent. Branch from `main` again when you create the exercise branch. Remove the contents of the Readme and put the exercise description in it, but don't reference exercise 03 (code completion exercise).

### Assistant
I read the overall plan and the refactoring exercise plan, and proposed a new exercise based on the duplicated `humanize(String pastryId)` methods in the matcher service package.

The plan was to create `exercise/06-refactoring` from `main`, keep the duplication in place as the learner’s refactoring target, and replace the README with a short standalone assignment that asked the user to make the logic DRY with a Coding Agent while preserving behavior.

### User
PLEASE IMPLEMENT THIS PLAN:
# Exercise 06 Plan: DRY Refactor of `humanize`

## Summary
Create `exercise/06-refactoring` from `main`.

Use the duplicated `humanize(String pastryId)` implementation in matcher-side services as the refactoring target. The exercise should ask participants to use a Coding Agent to remove duplication safely, keep behavior unchanged, and verify the code still builds. The branch README should describe this as a standalone refactoring task and should not mention the earlier code-completion exercise.

## Key Changes
- Create branch `exercise/06-refactoring` from `main`, not from any prior exercise or solution branch.
- Keep the duplicated `humanize` methods in place on the exercise branch so learners have a real refactoring target.
- Do not change REST contracts, OpenAPI files, persistence schema, Kafka behavior, or user-facing functionality in the exercise seed.
- Replace `README.md` contents with a short exercise brief that includes:
  - link to `main` for full context
  - assignment asking the user to make the duplicated `humanize` logic DRY with a Coding Agent
  - instruction to preserve behavior and keep the refactor small/reviewable
  - link to `solution/06-refactoring`
- Frame the task around the duplication in:
  - `matcher-api/src/main/java/com/pastryvibe/matcher/service/RatingIngestionService.java`
  - `matcher-api/src/main/java/com/pastryvibe/matcher/service/BaselineDataSeeder.java`

## Exercise Description Content
- Tell the learner to identify the duplicated `humanize` logic in `matcher-api`.
- Ask them to extract or centralize that logic so both callers share one implementation.
- Instruct them to avoid feature changes and preserve the existing output for names like `STROOPWAFEL`, `APPELFLAP`, and underscore-separated pastry names.
- Suggest using a Coding Agent for a safe refactor prompt and verifying the build afterwards.

## Test Plan
- Confirm the new branch starts cleanly from `main`.
- Verify the exercise branch itself still builds before any learner changes.
- Acceptance criteria for the eventual solution:
  - only duplication removal / structure cleanup
  - both original call sites use shared logic
  - build/checks remain green
  - no behavior change in pastry display-name formatting

## Assumptions
- The exercise seed should expose the duplication directly rather than pre-introducing a helper.
- README should stay short and concise, matching the repo convention for `exercise/*` branches.
- No explicit reference to exercise 03 should appear in the README or exercise framing.

### Assistant
I created `exercise/06-refactoring` from `main`, left the duplicate `humanize` methods untouched as the exercise target, replaced the README with a short standalone refactoring assignment, and verified the branch with `mvn -q -DskipTests package`.

### User
Great, I've pushed these changes. Now create a solution branch based on this and let a subagent perform the exercise. Verify the output of the agent and when correct: add the literal prompt that you provided to the agent to the Readme.

### Assistant
I created `solution/06-refactoring` from the current `exercise/06-refactoring` branch and delegated the refactor to a subagent with a tightly scoped prompt. After the subagent completed, I reviewed the resulting changes myself, reran `mvn -q -DskipTests package`, and confirmed the refactor was correct.

I then updated the solution README with a concise summary, the literal prompt I had provided to the subagent, and the rationale for that prompt.
