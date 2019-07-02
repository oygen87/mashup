package com.example.cygnimashup.model.musicbrainz;

public class Relation {
    private String type;
    private WikidataUrl url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WikidataUrl getUrl() {
        return url;
    }

    public void setUrl(WikidataUrl url) {
        this.url = url;
    }
}
