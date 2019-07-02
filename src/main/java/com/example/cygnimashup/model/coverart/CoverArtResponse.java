package com.example.cygnimashup.model.coverart;

import java.util.List;

public class CoverArtResponse {
    private List<Image> images;
    private String release;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
