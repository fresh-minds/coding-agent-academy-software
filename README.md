# Exercise 01 - Debugging Out-of-Order Reviews

For full project setup and baseline context, see the `main` branch.

## Assignment (Bug Report)

### Summary
The persisted "last review" for a pastry is occasionally incorrect after multiple quick submissions.

### Expected
The most recently submitted review for a pastry should be stored as that pastry's last review.

### Actual
An older review can end up stored as the pastry's last review.

### Impact
Users may receive stale or incorrect recommendation context based on outdated review data.

### Hints (Non-Spoiler)
- Trace the event flow from submission to persistence.
- Check whether ordering assumptions align with per-pastry update expectations.
- Inspect how the consumer writes the "last review" state.

## Scope
- Diagnose the likely cause of the issue.
- Implement a targeted fix that improves per-pastry ordering behavior.
- Keep architecture and endpoint contracts unchanged.

## Done Criteria
- Root cause is identified and documented in your branch notes.
- Fix is implemented and explained clearly.
- Existing local flows still run.
- This exercise README links to the matching solution branch.

## Solution Branch
- `solution/01-debugging`
