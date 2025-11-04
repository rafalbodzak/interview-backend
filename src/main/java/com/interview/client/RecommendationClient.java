package com.interview.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendationClient {

    private final RestTemplate restTemplate;

    @Value("${recommendation.service.url:http://localhost:8081}")
    private String recommendationServiceUrl;

    public List<Long> getRecommendations(Long trackId) {
        try {
            String url = recommendationServiceUrl + "/api/recommendations/" + trackId;
            log.info("Fetching recommendations from: {}", url);
            
            Long[] recommendations = restTemplate.getForObject(url, Long[].class);
            return recommendations != null ? List.of(recommendations) : List.of();
        } catch (Exception e) {
            log.error("Failed to fetch recommendations", e);
            return List.of();
        }
    }
}
