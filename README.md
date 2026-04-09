# Exercise 06: Refactoring

Full project context lives on [`main`](https://github.com/fresh-minds/coding-agent-academy-software/tree/main).

Use a Coding Agent to refactor the duplicated `humanize(String pastryId)` logic in `matcher-api` so both matcher services share one implementation. Start by looking at `RatingIngestionService` and `BaselineDataSeeder`.

Keep the refactor small and behavior-preserving. The formatting for pastry names such as `STROOPWAFEL`, `APPELFLAP`, and underscore-separated names should stay the same, and the project should still build afterwards.

Reference solution: [`solution/06-refactoring`](https://github.com/fresh-minds/coding-agent-academy-software/tree/solution/06-refactoring)
