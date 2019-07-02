package com.example.cygnimashup.model.musicbrainz;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MusicBrainzResponse {
    @JsonProperty("release-groups")
    private List<ReleaseGroup> releaseGroups;
    private List<Relation> relations;
    private String name;

    public List<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
