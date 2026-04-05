package com.pastryvibe.matcher.api;

import com.pastryvibe.matcher.service.BestRatedPastryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pastries")
public class BestRatedPastryController {

    private final BestRatedPastryService bestRatedPastryService;

    public BestRatedPastryController(BestRatedPastryService bestRatedPastryService) {
        this.bestRatedPastryService = bestRatedPastryService;
    }

    @GetMapping("/best-rated")
    public BestRatedPastryResponse getBestRatedPastry() {
        return bestRatedPastryService.getBestRatedPastry();
    }
}
