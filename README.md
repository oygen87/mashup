## Musicbrainz webservice aggregator

Spring Boot REST Service.

Returning aggregated musical information about an artists or group from multiple data based on given MBID.


## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `CygniMashupApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

VM Options `dev` profile for debug logs `-Dspring.profiles.active=dev`

## Example usage

Webservice accepts GET requests to `/{mbid}`

[mbid docs](https://musicbrainz.org/doc/MusicBrainz_Identifier)

Example request : 

`localhost:8080/f27ec8db-af05-4f36-916e-3d57f91ecf5e`

Example response : 

```
{
    "mbid": "f27ec8db-af05-4f36-916e-3d57f91ecf5e",
    "description": "<p class=\"mw-empty-elt\">\n</p>\n\n\n<p class=\"mw-empty-elt\">\n\n\n</p>\n<p><b>Michael Joseph Jackson</b> (August 29, 1958 – June 25, 2009) was an American singer, songwriter, and dancer. Dubbed the \"King of Pop\", he is regarded as one of the most significant cultural figures of the 20th century and one of the greatest entertainers. Jackson's contributions to music, dance, and fashion, along with his publicized personal life, made him a global figure in popular culture for over four decades. \n</p><p>The eighth child of the Jackson family, Michael made his professional debut in 1964 with his elder brothers Jackie, Tito, Jermaine, and Marlon as a member of the Jackson 5. He began his solo career in 1971 while at Motown Records, and in the early 1980s, became a dominant figure in popular music. His music videos, including those for \"Beat It\", \"Billie Jean\", and \"Thriller\" from his 1982 album <i>Thriller</i>, are credited with breaking racial barriers and transforming the medium into an art form and promotional tool. Their popularity helped bring the television channel MTV to fame. <i>Bad</i> (1987) was the first album to produce five US <i>Billboard</i> Hot 100 number-one singles. He continued to innovate throughout the 1990s with videos such as \"Black or White\" and \"Scream\", and forged a reputation as a touring artist. Through stage and video performances, Jackson popularized complicated dance techniques such as the robot and the moonwalk, to which he gave the name. His sound and style have influenced artists of various genres.\n</p><p>Jackson is one of the best-selling music artists of all time, with estimated sales of over 350 million records worldwide; <i>Thriller</i> is the best-selling album of all time, with estimated sales of 66 million copies worldwide. His other albums, including <i>Off the Wall</i> (1979), <i>Bad</i> (1987), <i>Dangerous</i> (1991), and <i>HIStory</i> (1995), also rank among the world's best-selling. He won hundreds of awards (more than any other artist in the history of popular music), has been inducted into the Rock and Roll Hall of Fame twice, and is the only pop or rock artist to have been inducted into the Dance Hall of Fame. His other achievements include Guinness world records (including the Most Successful Entertainer of All Time), 15 Grammy Awards (including the Legend and Lifetime Achievement awards), 26 American Music Awards (more than any other artist), and 13 number-one US singles (more than any other male artist in the Hot 100 era). Jackson was the first artist to have a top ten single in the <i>Billboard</i> Hot 100 in five different decades. In 2016, his estate earned $825 million, the highest yearly amount for a celebrity ever recorded by <i>Forbes</i>.\n</p><p>In the late 1980s, Jackson became a figure of controversy for his changing appearance, relationships, behavior and lifestyle. In 1993, he was accused of sexually abusing the child of a family friend. The accusation was settled out of court. In 2005, he was tried and acquitted of further child sexual abuse allegations and several other charges. In 2009, while preparing for a series of comeback concerts, <i>This Is It</i>, Jackson died from an overdose of sedatives administered by his personal physician, Conrad Murray. Jackson's fans around the world expressed their grief, and his public memorial service was broadcast live. In 2019, the documentary <i>Leaving Neverland</i> detailed renewed allegations of child sexual abuse and led to an international backlash against Jackson.\n</p>",
    "albums": [
        {
            "id": "ee749c63-5699-38e0-b565-7e84414648d9",
            "title": "Off the Wall",
            "image": "http://coverartarchive.org/release/6258e39d-bef4-4d5a-a654-440cf4c4c29a/5349015874.jpg"
        },
        {
            "id": "f32fab67-77dd-3937-addc-9062e28e4c37",
            "title": "Thriller",
            "image": "http://coverartarchive.org/release/959272f9-97ae-4179-aebe-950eef64ed93/2700090127.jpg"
        },
        {
            "id": "ffc3f8b5-7b22-40ed-8867-0cad52b6b2ae",
            "title": "18 Greatest Hits",
            "image": "http://coverartarchive.org/release/75276adf-7504-4bba-8630-631ef020c31b/1871938266.jpg"
        }
        more albums ...
    ]
}
```

## Example artists :

| Artist            | mbid                                  |
|-------------------|---------------------------------------|
| Mötley Crüe       | 26f07661-e115-471d-a930-206f5c89d17c  |
| Michael Jackson   | f27ec8db-af05-4f36-916e-3d57f91ecf5e  |
| The Beatles       | b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d  |
