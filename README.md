# Exercise 01 - Debugging Out-of-Order Reviews

For full project setup and baseline context, see the `main` branch.

## Assignment

There is a bug in one of these services. See the bug report below.
It's your task to use a Coding Agent to find and fix the bug.

The solution, including an example prompt, can be found in the `solution/01-debugging` branch.

### Summary
The persisted "last review" for a pastry is occasionally incorrect after multiple quick submissions.

### Expected
The most recently submitted review for a pastry should be stored as that pastry's last review.

### Actual
An older review can end up stored as the pastry's last review.

### Impact
Users may receive stale or incorrect recommendation context based on outdated review data.
