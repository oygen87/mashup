package com.example.cygnimashup.model.wikidata;

import java.util.Map;

public class WikidataResponse {
    private Map<String, WikidataEntity> entities;

    public Map<String, WikidataEntity> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, WikidataEntity> entities) {
        this.entities = entities;
    }
}
