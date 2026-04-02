# Exercise 02 - Javadoc

Use a Coding Agent to add or improve Javadoc across the codebase.
Document all non-trivial functions, while skipping trivial functions such as getters and setters.
Keep the documentation concise and focused on intent, constraints, behavior, and important edge cases.
Do not make functional behavior changes.
HTML in Javadocs is discouraged, so avoid tags like `<p>`.

## Solution

Prompt:

```text
You are working in /Users/rdebokx/workspace/coding-agent-academy-software on branch solution/02-javadoc. You are not alone in the codebase: do not revert others' edits, and adjust to any changes you find.

Task: create the solution for Exercise 02 from scratch.

Requirements:
- Add or improve Javadoc across the touched ratings-api and matcher-api classes.
- Document every non-trivial function in the files you touch, including important private helpers.
- Skip trivial getters, setters, constructors, and similarly self-evident boilerplate.
- Place method Javadocs above annotations such as @GetMapping, @PostMapping, @Override, @KafkaListener, and @Transactional.
- Keep docs concise, accurate, and high-signal. Explain intent, constraints, side effects, error behavior, and edge cases where it matters.
- Do not use HTML in Javadoc. Avoid tags like <p>; use plain prose instead.
- Do not change functional behavior.

README requirements:
- Keep the existing exercise description short.
- Update the exercise description so it explicitly says HTML in Javadocs is discouraged and tags like <p> should not be used.
- Add a `## Solution` section that stores the final prompt you used and a short rationale.
- The prompt stored in the README must be the exact final prompt that produced the result.

Priorities:
1. Public controllers, services, and DTOs that define API boundaries.
2. Non-trivial private helpers in touched service classes.
3. Avoid comments that restate obvious code.

Suggested targets include RatingController, RatingPublisherService, SuggestionController, SuggestionService, RatingIngestionService, and BaselineDataSeeder.

After editing, run validation (for example `mvn -q -DskipTests compile`) and report back with:
- a short summary of what you changed
- whether the result avoids HTML tags in Javadocs
- validation run
- the exact final prompt
- files changed
```

Rationale: this prompt keeps the work focused on the public API and non-trivial helpers, while making the no-HTML rule explicit.
