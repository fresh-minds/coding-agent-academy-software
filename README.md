# Java Coding Agent Academy

A hands-on training repository for Java consultants to practice coding-agent workflows on a realistic, event-driven baseline.

## Assignment

The baseline on `main` is intentionally designed as the source world for exercises:

- Two Spring Boot 3 services (`Java 21`, `Maven`):
  - `ratings-api`: accepts pastry ratings, validates input, and publishes rating events to Kafka.
  - `matcher-api`: consumes rating events, persists data in MySQL, and returns pastry suggestions.
- OpenAPI-first contracts are stored in `openapi/` and are the API source of truth.
- Docker Compose provides local Kafka + MySQL infrastructure.
- A medium seed dataset is included (20 pastries, 15 users, 1-10 ratings per pastry).
- Controlled pastry and flavor enums are used end to end.

### Required baseline bug (intentional)

For Exercise 01, `main` intentionally contains an ordering defect:

- Producer in `ratings-api` publishes events **without a pastry-based Kafka key**.
- This means per-pastry ordering is not guaranteed.
- In quick successive updates, the persisted "last review" in `matcher-api` can become stale.

Do not "pre-fix" this on `main`; the debugging exercise starts from this defect.

## Run Locally

1. Start infra:
   ```bash
   docker compose up -d
   ```
2. Start matcher service:
   ```bash
   mvn -pl matcher-api spring-boot:run
   ```
3. Start ratings service (new terminal):
   ```bash
   mvn -pl ratings-api spring-boot:run
   ```
4. Use request collections:
   - IntelliJ HTTP client: `collections/intellij/http-requests.http`
   - Postman collection: `collections/postman/pastry-agent-academy.postman_collection.json`

## Branch Structure

Participants should **fork first** and work in their own fork.

- `main`
  - Baseline implementation used as the starting point for all exercises.
- `plans`
  - Planning-only branch that contains exercise planning documents (`01-overall-plan.md`, `02-...`, etc.).
  - These plan files intentionally stay out of `main`.
- `exercise/NN-topic`
  - Exercise branch for assignment `NN`.
  - Always branched from `main`.
- `solution/NN-topic`
  - Matching solution branch for each exercise.
  - Branched from the corresponding `exercise/NN-topic` branch.
- `exercise/99-vibing-assignment`
  - Capstone branch where participants create a new training repo.
- `solution/99-vibing-assignment`
  - Capstone solution branch.

### Exercise Order

1. Debugging code
2. Javadoc
3. IDE code completion (IntelliJ + VS Code)
4. Unit + integration tests
5. Code review
6. Refactoring
7. Repetitive task automation
8. Skills/playbooks
9. CI pipeline creation
10. Feature implementation (plan-first)
99. Capstone vibing assignment
