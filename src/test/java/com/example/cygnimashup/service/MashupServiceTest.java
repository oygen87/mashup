package com.example.cygnimashup.service;

import com.example.cygnimashup.dao.CoverArtDao;
import com.example.cygnimashup.dao.MusicBrainzDao;
import com.example.cygnimashup.dao.WikidataDao;
import com.example.cygnimashup.dao.WikipediaDao;
import com.example.cygnimashup.model.Album;
import com.example.cygnimashup.model.coverart.CoverArtResponse;
import com.example.cygnimashup.model.coverart.Image;
import com.example.cygnimashup.model.musicbrainz.MusicBrainzResponse;
import com.example.cygnimashup.model.musicbrainz.Relation;
import com.example.cygnimashup.model.musicbrainz.ReleaseGroup;
import com.example.cygnimashup.model.musicbrainz.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MashupServiceTest {

    @Mock
    CoverArtDao coverArtDao;
    @Mock
    MusicBrainzDao musicBrainzDao;
    @Mock
    WikidataDao wikidataDao;
    @Mock
    WikipediaDao wikipediaDao;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldParseIdentifierFromUrl() {
        Url url = new Url();
        url.setResource("https://en.wikipedia.org/wiki/The_Beatles");

        Relation relation = new Relation();
        relation.setUrl(url);

        String result = MashupService.parseIdentifierFromUrl(relation);
        String expected = "The_Beatles";

        assertEquals(expected, result);
    }

    @Test
    public void shouldParseIdentifierFromUrlForWikipediaDirectLink() {
        Url url = new Url();
        url.setResource("https://www.wikidata.org/wiki/Q370293");

        Relation relation = new Relation();
        relation.setUrl(url);

        String expected = "Q370293";
        String result = MashupService.parseIdentifierFromUrl(relation);

        assertEquals(expected, result);
    }

    @Test
    public void shouldAddImageUrlToAlbums() {
        MashupService mashupService = new MashupService(musicBrainzDao, coverArtDao, wikipediaDao, wikidataDao);

        List<Album> albums = new ArrayList<>();
        albums.add(new Album("111", "Greatest Hits vol.4", null));

        CoverArtResponse coverArtResponse = new CoverArtResponse();
        Image img1 = new Image();
        img1.setFront(false);
        img1.setImage("http://localhost.com/backimage.jpg");

        Image img2 = new Image();
        img2.setFront(true);
        img2.setImage("http://localhost.com/firstfrontimage.jpg");
        List<Image> images = new ArrayList<>();
        images.add(img1);
        images.add(img2);
        coverArtResponse.setImages(images);
        when(coverArtDao.getCoverArt("111")).thenReturn(coverArtResponse);

        String expected = "http://localhost.com/firstfrontimage.jpg";
        List<Album> result = mashupService.addImageUrlsToAlbums(albums);

        assertEquals(expected, result.get(0).getImage());
        assertEquals(1, result.size());
    }

    @Test
    public void shouldHandleNullWhenGettingCoverArt() {
        MashupService mashupService = new MashupService(musicBrainzDao, coverArtDao, wikipediaDao, wikidataDao);

        List<Album> albums = new ArrayList<>();
        albums.add(new Album("111", "Greatest Hits vol.1", null));

        when(coverArtDao.getCoverArt("111")).thenReturn(null);

        Album result = mashupService.addImageUrlsToAlbums(albums).get(0);
        Album expected = albums.get(0);

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnNullWhenExtractingAlbumsFromEmptyMusicBrainzAlbumList() {
        MashupService mashupService = new MashupService(musicBrainzDao, coverArtDao, wikipediaDao, wikidataDao);

        List<ReleaseGroup> releaseGroups = new ArrayList<>();
        MusicBrainzResponse musicBrainzResponse = new MusicBrainzResponse();
        musicBrainzResponse.setReleaseGroups(releaseGroups);

        List<Album> result = mashupService.extractAlbums(musicBrainzResponse);

        assertNull(result);
    }

    @Test
    public void shouldFindDirectWikipediaLink() {
        List<Relation> relations = new ArrayList<>();

        Relation relation1 = new Relation();
        relation1.setType("MySpace");
        Url url1 = new Url();
        url1.setResource("http://myspace.com/wiki/ABC123");
        relation1.setUrl(url1);

        Relation relation2 = new Relation();
        relation2.setType("Youtube");
        Url url2 = new Url();
        url2.setResource("http://youtube.com/movies/en/wiki/FD5gGF4jk5");
        relation2.setUrl(url2);

        Relation relation3 = new Relation();
        relation3.setType("wikipedia");
        Url url3 = new Url();
        url3.setResource("https://en.wikipedia.org/wiki/The_Beatles");
        relation3.setUrl(url3);

        relations.add(relation1);
        relations.add(relation2);
        relations.add(relation3);

        MusicBrainzResponse musicBrainzResponse = new MusicBrainzResponse();
        musicBrainzResponse.setRelations(relations);

        String result = MashupService.checkDirectWikipediaLink(musicBrainzResponse);
        String expected = "The_Beatles";

        assertEquals(expected, result);
    }

    @Test
    public void shouldNotFindDirectWikipediaLinkAndReturnNull() {
        List<Relation> relations = new ArrayList<>();

        Relation relation1 = new Relation();
        relation1.setType("MySpace");
        Url url1 = new Url();
        url1.setResource("http://myspace.com/wiki/ABC123");
        relation1.setUrl(url1);

        Relation relation2 = new Relation();
        relation2.setType("Youtube");
        Url url2 = new Url();
        url2.setResource("http://youtube.com/movies/en/wiki/FD5gGF4jk5");
        relation2.setUrl(url2);

        relations.add(relation1);
        relations.add(relation2);

        MusicBrainzResponse musicBrainzResponse = new MusicBrainzResponse();
        musicBrainzResponse.setRelations(relations);

        String result = MashupService.checkDirectWikipediaLink(musicBrainzResponse);

        assertNull(result);
    }

    @Test
    public void shouldExtractWikidataId() {
        List<Relation> relations = new ArrayList<>();

        Relation relation1 = new Relation();
        relation1.setType("MySpace");
        Url url1 = new Url();
        url1.setResource("http://myspace.com/wiki/ABC123");
        relation1.setUrl(url1);

        Relation relation2 = new Relation();
        relation2.setType("wikidata");
        Url url2 = new Url();
        url2.setResource("https://www.wikidata.org/wiki/Q370293");
        relation2.setUrl(url2);

        relations.add(relation1);
        relations.add(relation2);

        MusicBrainzResponse musicBrainzResponse = new MusicBrainzResponse();
        musicBrainzResponse.setRelations(relations);

        String result = MashupService.extractWikidataId(musicBrainzResponse);
        String expected = "Q370293";

        assertEquals(expected, result);
    }
}