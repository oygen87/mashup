package com.example.cygnimashup.dao;

import com.example.cygnimashup.model.wikidata.WikidataResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WikidataDao {

    public WikidataResponse getWikiData(String id){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForEntity("https://www.wikidata.org/w/api.php?action=wbgetentities&ids={id}&format=json&props=sitelinks", WikidataResponse.class, id)
                .getBody();
    }
}
