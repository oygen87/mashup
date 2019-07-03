package com.example.cygnimashup.controller;

import com.example.cygnimashup.model.Mashup;
import com.example.cygnimashup.service.MashupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MashupController {

    private final MashupService mashupService;

    @Autowired
    public MashupController(MashupService mashupService) {
        this.mashupService = mashupService;
    }

    /**
     * Given a valid mbid,
     * Returns json model of artist with aescription, album releases and cover art.
     *
     * @param mbid @see <a href="https://musicbrainz.org/doc/MusicBrainz_Identifier">MusicBrainz Identifier</a>
     * @return {@link com.example.cygnimashup.model.Mashup}
     */

    @GetMapping("/{mbid}")
    public Mashup getMashupInfo(@PathVariable String mbid){
        return mashupService.getMashup(mbid);
    }
}
