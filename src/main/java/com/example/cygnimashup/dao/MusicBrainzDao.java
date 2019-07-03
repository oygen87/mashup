package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.musicbrainz.MusicBrainzResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MusicBrainzDao {

    /**
     * Returns album releases of given artist.
     * Throws 400 Bad Request when mbid is invalid.
     * Throws 404 Not Found when artist not found.
     *
     * @param mbid @see <a href="https://musicbrainz.org/doc/MusicBrainz_Identifier">MusicBrainz Identifier</a>
     * @return {@link com.example.cygnimashup.model.musicbrainz.MusicBrainzResponse}
     */
    public MusicBrainzResponse getArtistInfo(String mbid){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForEntity("http://musicbrainz.org/ws/2/artist/{mbid}?&fmt=json&inc=url-rels+release-groups", MusicBrainzResponse.class, mbid)
                .getBody();
    }
}
