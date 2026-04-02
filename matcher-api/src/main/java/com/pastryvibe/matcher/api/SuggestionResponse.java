package com.pastryvibe.matcher.api;

import java.util.List;

/**
 * Container response for ordered pastry suggestions.
 */
public record SuggestionResponse(List<PastrySuggestion> suggestions) {
}
