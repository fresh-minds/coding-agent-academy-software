# Java Coding Agent Academy

Hi there!

This is our FreshMinds Coding Agent Academy. This repository contains a bunch of exercises to get you familiar with the basics of coding-agent workflows.

This particular repository focuses on the Software Unit, which is why it is written in **Java**. But the good part is: with Coding Agents, it makes less and less of a difference whether you're fluent in Java, Python, .NET, Rust, or any other language.

If you have any questions or suggestions, please reach out to one of the Principals: Christophe Keteleer, Jeroen Rosenberg or Roy de Bokx.

## Assignments

**Important**: Fork this repository to your own GH account before you start on anything. This is to avoid this repo getting cluttered with everyone's solutions. 

We have structured this repo as follows:

* In this `main` branch, you will find the baseline applications as well as these general instructions.
* We have XXX (TODO) assignments lined up for you as listed below. For each assignment, you'll find:
  * A branch containing the assignment, for instance (TODO). This branch contains the code that you'll need (usually the same baseline application) and a Readme describing the exercise.
  * A branch containing the solution, for instance (TODO). This contains the solution in terms of generated output, as well as an example of the prompt that we used to generate the solution. You can use it to compare you own solution with ours.
* As a finishing touch, if you think you truly master Agentic Coding, we've kept a nice capstone assignment for you to complete: Vibecode your own training repo. You can find this in the branch (TODO). Obviously, our solution to this assignment is right before you.
* For the curious: we keep the planning files and prompts (where possible) in the `plans` branch.

We have gathered the following exercises:

* [Exercise 01: Debugging](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/01-debugging)
* [Exercise 02: Javadoc](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/02-javadoc)
* [Exercise 03: IDE Code Completion](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/03-ide-code-completion)
* [Exercise 04: Tests](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/04-tests)
* [Exercise 05: Code Review](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/05-code-review)
* [Exercise 06: Refactoring](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/06-refactoring)
* [Exercise 07: Repetitive Tasks](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/07-repetitive-tasks)
* [Exercise 08: Skills and Playbooks](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/08-skills-playbooks)
* [Exercise 09: CI Pipeline Creation](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/09-ci-pipeline-creation)
* [Exercise 10: Feature Implementation (Plan-First)](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/10-feature-implementation-plan-first)
* [Exercise 99: Vibing Assignment](https://github.com/fresh-minds/coding-agent-academy-software/tree/exercise/99-vibing-assignment)

### Getting started

* Pick your weapon of choice: Claude Code, ChatGPT Codex, Junie, or any other coding agent. Make sure you have your Coding Agent installed and ready.
* **Fork this repository to your own GH account, including all branches.** Clone your fork to your local machine.
* Get familiar with the baseline application (see below).
* Checkout the exercise branch you want to work on and follow the instructions.

## FreshMinds Pastry Platform

This training app simulates a tiny pastry recommendation platform with two cooperating services:

- `ratings-api` accepts pastry reviews from users. Each review contains a pastry identifier, a 1-5 score, up to 3 flavor tags, and free-text experience notes.
- `matcher-api` consumes these rating events, stores them in MySQL, and returns pastry suggestions based on requested flavors.

Functional use case covered:

- A user rates pastries they tried ("loved the caramel crunch", "too sweet today", etc.).
- Another user asks for suggestions using one to three flavors.
- The platform returns pastries that match at least one requested flavor.
- Results are ranked by:
  1. average rating (primary)
  2. number of overlapping flavors (secondary)

In short: this baseline models a realistic "ingest events -> materialize read model -> query ranked suggestions" workflow, but keeps the business domain playful and approachable for agent practice.

Tech stack at a glance:

- Java 21 + Spring Boot 3
- Maven multi-module setup
- Apache Kafka for async event flow
- MySQL + Spring Data JPA for persistence
- OpenAPI contracts in `openapi/`
- Docker Compose for local infrastructure


### Run Locally

1. Start kafka and MySQL:
   ```bash
   docker compose up -d
   ```
2. Start matcher service:
   ```bash
   mvn -pl matcher-api spring-boot:run
   ```
3. Start ratings service:
   ```bash
   mvn -pl ratings-api spring-boot:run
   ```
4. Send a request using one of the request collections:
   - IntelliJ HTTP client: `collections/intellij/http-requests.http`
   - Postman collection: `collections/postman/pastry-agent-academy.postman_collection.json`
