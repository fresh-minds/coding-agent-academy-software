package com.pastryvibe.matcher.api;

import com.pastryvibe.matcher.domain.FlavorTag;
import com.pastryvibe.matcher.service.SuggestionService;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes the pastry suggestion endpoint used by the matcher API.
 */
@RestController
@RequestMapping("/api/v1/suggestions")
@Validated
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    /**
     * Returns pastry suggestions for up to three requested flavors.
     *
     * @param flavors requested flavor tags from the client
     * @return ordered suggestion list
     */
    @GetMapping
    public SuggestionResponse suggest(
            @RequestParam("flavors") @Size(max = 3) List<FlavorTag> flavors
    ) {
        return suggestionService.suggest(flavors);
    }
}
