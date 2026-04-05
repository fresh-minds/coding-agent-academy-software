package com.pastryvibe.matcher.api;

import com.pastryvibe.matcher.domain.PastryId;
import com.pastryvibe.matcher.service.BestRatedPastryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BestRatedPastryControllerTest {

    @Test
    void exposesBestRatedPastryEndpoint() throws Exception {
        BestRatedPastryService service = new StubBestRatedPastryService(
                new BestRatedPastryResponse(
                        PastryId.STROOPWAFEL,
                        "Stroopwafel",
                        4.7,
                        9,
                        "Great texture and balanced sweetness."
                )
        );

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new BestRatedPastryController(service)).build();

        mockMvc.perform(get("/api/v1/pastries/best-rated").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pastryId").value("STROOPWAFEL"))
                .andExpect(jsonPath("$.pastryName").value("Stroopwafel"))
                .andExpect(jsonPath("$.averageRating").value(4.7))
                .andExpect(jsonPath("$.ratingCount").value(9))
                .andExpect(jsonPath("$.lastReviewDescription").value("Great texture and balanced sweetness."));
    }

    @Test
    void returnsNotFoundWhenServiceCannotFindRatedPastry() throws Exception {
        BestRatedPastryService service = new StubBestRatedPastryService(
                new ResponseStatusException(NOT_FOUND, "No rated pastry found.")
        );

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new BestRatedPastryController(service)).build();

        mockMvc.perform(get("/api/v1/pastries/best-rated").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static final class StubBestRatedPastryService extends BestRatedPastryService {

        private final BestRatedPastryResponse response;
        private final ResponseStatusException exception;

        private StubBestRatedPastryService(BestRatedPastryResponse response) {
            super(null, null);
            this.response = response;
            this.exception = null;
        }

        private StubBestRatedPastryService(ResponseStatusException exception) {
            super(null, null);
            this.response = null;
            this.exception = exception;
        }

        @Override
        public BestRatedPastryResponse getBestRatedPastry() {
            if (exception != null) {
                throw exception;
            }
            return response;
        }
    }
}
