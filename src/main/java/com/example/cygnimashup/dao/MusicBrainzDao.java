package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.musicbrainz.MusicBrainzResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MusicBrainzDao {

    public MusicBrainzResponse getArtistInfo(String mbid){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForEntity("http://musicbrainz.org/ws/2/artist/{mbid}?&fmt=json&inc=url-rels+release-groups", MusicBrainzResponse.class, mbid)
                .getBody();
    }
}
