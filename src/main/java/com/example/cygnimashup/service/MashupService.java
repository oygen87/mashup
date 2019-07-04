package com.example.cygnimashup.service;

import com.example.cygnimashup.dao.CoverArtDao;
import com.example.cygnimashup.dao.MusicBrainzDao;
import com.example.cygnimashup.dao.WikidataDao;
import com.example.cygnimashup.dao.WikipediaDao;
import com.example.cygnimashup.model.*;
import com.example.cygnimashup.model.coverart.CoverArtResponse;
import com.example.cygnimashup.model.coverart.Image;
import com.example.cygnimashup.model.musicbrainz.MusicBrainzResponse;
import com.example.cygnimashup.model.musicbrainz.Relation;
import com.example.cygnimashup.model.wikidata.WikidataResponse;
import com.example.cygnimashup.model.wikipedia.WikipediaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MashupService {

    static final String WIKIPEDIA = "wikipedia";
    static final String WIKIDATA = "wikidata";

    private static final Logger logger = LoggerFactory.getLogger(MashupService.class);

    private final MusicBrainzDao musicBrainzDao;
    private final CoverArtDao coverArtDao;
    private final WikipediaDao wikipediaDao;
    private final WikidataDao wikidataDao;

    @Autowired
    public MashupService(MusicBrainzDao musicBrainzDao, CoverArtDao coverArtDao, WikipediaDao wikipediaDao, WikidataDao wikidataDao) {
        this.musicBrainzDao = musicBrainzDao;
        this.coverArtDao = coverArtDao;
        this.wikipediaDao = wikipediaDao;
        this.wikidataDao = wikidataDao;
    }

    // TODO : persist cache between reboots
    @Cacheable("mashup-cache")
    public Mashup getMashup(String mbid) {
        // The return model
        Mashup model = new Mashup();
        model.setMbid(mbid);

        // Get artist information and album releases
        MusicBrainzResponse musicBrainzResponse = musicBrainzDao.getArtistInfo(mbid);

        // Extract albums from musicbrainz response and add cover art
        List<Album> albums = extractAlbums(musicBrainzResponse);
        model.setAlbums(albums);

        // Check if there is direct link with identifier to Wikipedia
        String directWikipediaLinkToArtist = checkDirectWikipediaLink(musicBrainzResponse);

        // Otherwise query Wikidata for full artist name identifier
        String artist = null;
        if (directWikipediaLinkToArtist == null) {
            String wikidataId = getIdentifier(musicBrainzResponse, WIKIDATA);
            WikidataResponse wikidataResponse = wikidataDao.getWikiData(wikidataId);
            artist = extractArtistName(wikidataResponse, wikidataId);
        }

        // Get description from Wikipedia
        WikipediaResponse wikipediaResponse = wikipediaDao.getDescription(directWikipediaLinkToArtist == null ? artist : directWikipediaLinkToArtist);
        String description = extractWikipediaDescription(wikipediaResponse);
        model.setDescription(description);

        logger.debug(model.toString());

        return model;
    }

    static String checkDirectWikipediaLink(MusicBrainzResponse musicBrainzResponse) {
        return getIdentifier(musicBrainzResponse, WIKIPEDIA);
    }

    static String getIdentifier(MusicBrainzResponse musicBrainzResponse, String relationSource) {
        try {
            Optional<Relation> wikidataRelation = musicBrainzResponse.getRelations()
                    .stream()
                    .filter(relation -> relation.getType().equals(relationSource))
                    .findFirst();

            return wikidataRelation.map(MashupService::parseIdentifierFromUrl).orElse(null);
        } catch (NullPointerException e) {
            return null;
        }
    }

    static String extractWikipediaDescription(WikipediaResponse wikipediaResponse) {
        try {
            return wikipediaResponse.getQuery().getPages().entrySet().iterator().next().getValue().getExtract();
        } catch (NullPointerException e) {
            return null;
        }
    }

    static String extractArtistName(WikidataResponse wikidataResponse, String id) {
        try {
            return wikidataResponse.getEntities().get(id).getSitelinks().getEnwiki().getTitle();
        } catch (NullPointerException e) {
            return null;
        }
    }

    List<Album> extractAlbums(MusicBrainzResponse musicBrainzResponse) {
        if (musicBrainzResponse.getReleaseGroups().isEmpty()) {
            return null;
        }

        List<Album> albums = musicBrainzResponse.getReleaseGroups()
                .stream()
                .map(r -> new Album(r.getId(), r.getTitle(), null))
                .collect(Collectors.toList());

        return addImageUrlsToAlbums(albums);
    }

    List<Album> addImageUrlsToAlbums(List<Album> albums) {
        return albums
                .stream()
                .map(album -> {
                    CoverArtResponse coverArtResponse = coverArtDao.getCoverArt(album.getId());
                    if (coverArtResponse == null) {
                        return album;
                    } else {
                        Optional<Image> img = coverArtResponse.getImages()
                                .stream()
                                .filter(Image::isFront)
                                .findFirst();

                        return new Album(album.getId(), album.getTitle(), img.map(Image::getImage).orElse(null));
                    }
                }).collect(Collectors.toList());
    }

    static String parseIdentifierFromUrl(Relation relation) {
        /*
        * The unique identifier for wikidata lookup and the wikipedia full name to article is parsed
        * from the relations sections in musicbrainz answer from a url as the second element when splitting on "/wiki/"
        * We don't get it as a single value of a key.
        *
        * Examples :
        * "https://www.wikidata.org/wiki/Q370293"
        * "https://en.wikipedia.org/wiki/John_Williams_(guitarist)"
        */
        return relation.getUrl().getResource().split("wiki/")[1];
    }
}
