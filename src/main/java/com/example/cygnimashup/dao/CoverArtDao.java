package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.coverart.CoverArtResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoverArtDao {

    /**
     * Returns cover art based on given mbid of a release album.
     * Returns Null if no cover art is found.
     *
     * @param mbid @see <a href="https://musicbrainz.org/doc/MusicBrainz_Identifier">MusicBrainz Identifier</a>
     * @return @return {@link com.example.cygnimashup.model.coverart.CoverArtResponse}
     */

    public CoverArtResponse getCoverArt(String mbid){
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForEntity("http://coverartarchive.org/release-group/{mbid}", CoverArtResponse.class, mbid).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
