package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.wikipedia.WikipediaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WikipediaDao {

    /**
     * Returns Description (wrapped in model) about an artist from Wikipedia.
     *
     * @param artist
     * @return {@link com.example.cygnimashup.model.wikipedia.WikipediaResponse}
     */
    public WikipediaResponse getDescription(String artist) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate
                    .getForEntity("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles={artist}", WikipediaResponse.class, artist)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
