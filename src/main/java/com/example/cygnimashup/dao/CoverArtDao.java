package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.coverart.CoverArtResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoverArtDao {

    public CoverArtResponse getCoverArt(String mbid){
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForEntity("http://coverartarchive.org/release-group/{mbid}", CoverArtResponse.class, mbid).getBody();
        } catch (Exception e) {
            // return null if no cover is found
            return null;
        }
    }
}
