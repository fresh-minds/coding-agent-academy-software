# Java Coding Agent Academy - Overall Plan

## Purpose
Create an agent-agnostic, Java-only training repository for consultants to practice realistic Coding Agent workflows end to end.

## Audience
- Expert Java consultants (strong with Java, Kafka, MySQL, Docker).
- Mixed Coding Agent maturity (first-time to experienced).

## Learning Model
- Self-paced.
- No grading.
- No submission requirement.
- Participants must **fork first** and work in their own fork to prevent clutter on the source repository.

## Repository Baseline (`main`)
- Two Spring Boot 3 services (`Java 21`, `Maven`):
  - `ratings-api`: accepts pastry ratings and publishes Kafka events.
  - `matcher-api`: consumes events, persists in MySQL, and returns pastry suggestions.
- OpenAPI-first REST contracts are the source of truth.
- Docker Compose provides Kafka + MySQL for local runs.
- Medium realistic seed dataset + HTTP request collection.
- Controlled pastry/flavor enums.
- Baseline is runnable but intentionally imperfect for training.

## Required Baseline Bug (for Exercise 01)
- Introduce an out-of-order event bug in `main`.
- Cause: Kafka producer publishes rating events **without pastry-based key**.
- Effect: for fast successive ratings on the same pastry, the persisted "last review" can be wrong.
- Important: code should contain this defect; no need to prove it deterministically in runtime.

## Branch Model
- Exercise branches: `exercise/NN-topic` (from `main` each time).
- Solution branches: `solution/NN-topic` (from matching exercise branch).
- Capstone exercise branch: `exercise/99-vibing-assignment`.
- Capstone solution branch: `solution/99-vibing-assignment`.

## Exercise Sequence (Least to Most Disruptive)
1. Debugging code
2. Javadoc
3. IDE code completion (IntelliJ + VS Code)
4. Unit + integration tests for existing code
5. Reviewing code
6. Refactoring code
7. Repetitive tasks automation
8. Skills/playbooks usage (agent-agnostic + tool appendix)
9. CI pipeline creation
10. Feature implementation (plan-first included)
99. Capstone vibe-coding assignment

## README Requirements Per Branch
- Every `exercise/*` README includes:
  - Link to `main` for full context.
  - Assignment description for that branch.
  - Link to matching `solution/*` branch.
- Every `solution/*` README includes:
  - Code/result summary.
  - Literal **subagent prompt** used.
  - Short rationale for that prompt.

## Technical Defaults
- Language: English only.
- No external runtime LLM dependency in the Java services.
- Integration test style: service-level tests with Testcontainers (Kafka + MySQL), not full dual-service E2E tests.
- CI is introduced as a dedicated exercise (create-only), not in initial baseline.

## Plan Files in This Branch
- `01-overall-plan.md` (this file)
- `02-exercise-01-debugging.md`
- `03-exercise-02-javadoc.md`
- `04-exercise-03-ide-code-completion.md`
- `05-exercise-04-tests.md`
- `06-exercise-05-code-review.md`
- `07-exercise-06-refactoring.md`
- `08-exercise-07-repetitive-tasks.md`
- `09-exercise-08-skills-playbooks.md`
- `10-exercise-09-ci-pipeline-creation.md`
- `11-exercise-10-feature-implementation-plan-first.md`
- `12-exercise-99-vibing-assignment.md`

## Build Validation Checklist for Future Sessions
- Each exercise plan includes: objective, starting branch, expected outcomes, hints, done criteria, solution-branch expectations.
- Branch naming and sequence are consistent.
- Debugging exercise contains bug-report style framing (symptom, expected vs actual, impact, hints).
- Main README explicitly explains fork-first workflow and branch navigation.
