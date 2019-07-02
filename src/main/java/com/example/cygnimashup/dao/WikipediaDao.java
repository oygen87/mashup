package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.wikipedia.WikipediaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WikipediaDao {

    public WikipediaResponse getDescription(String artist) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForEntity("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles={artist}", WikipediaResponse.class, artist)
                .getBody();
    }
}
