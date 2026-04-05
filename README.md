# Solution 06: Refactoring

Extracted the duplicated `humanize(String pastryId)` logic from the two matcher services into a shared package-private `PastryNameFormatter`, so the code is DRY while preserving the existing pastry-name formatting behavior.

Literal subagent prompt used:
`Refactor the duplicated humanize(String pastryId) logic in the matcher service package on the current branch. You own only these files: matcher-api/src/main/java/com/pastryvibe/matcher/service/RatingIngestionService.java, matcher-api/src/main/java/com/pastryvibe/matcher/service/BaselineDataSeeder.java, and any new helper you need to add under matcher-api/src/main/java/com/pastryvibe/matcher/service/. Do not edit README. Preserve behavior exactly: names like STROOPWAFEL, APPELFLAP, and underscore-separated pastry ids must format the same as before. Keep the refactor small and reviewable. You are not alone in the codebase: do not revert unrelated edits, and adjust to any changes you find rather than overwriting them. When done, report the files you changed and a short summary of the refactor.`

Rationale:
This prompt narrows the write scope, states the exact behavior that must stay unchanged, and frames the work as a small safe refactor, which makes it well suited for delegated agent work.
