package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.wikidata.WikidataResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WikidataDao {

    /**
     * Returns Wikipedia identifier (wrapped in model) to fetch description for an artist.
     *
     * @param id Wikidata Identifier found in MusicBrainzResponse
     * @return {@link com.example.cygnimashup.model.wikidata.WikidataResponse}
     */

    public WikidataResponse getWikiData(String id){
        if(id == null) {
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForEntity("https://www.wikidata.org/w/api.php?action=wbgetentities&ids={id}&format=json&props=sitelinks", WikidataResponse.class, id)
                .getBody();
    }
}
