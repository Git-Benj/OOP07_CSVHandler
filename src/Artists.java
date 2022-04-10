/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 10.04.2022
 * Last Change: 10.04.2022
 */

import java.util.*;

public class Artists {
    public static HashMap<String, Artist> artists = new HashMap<>();
    public static ArrayList<Artist> artistArray = null;

    //prints artist into console
    public static void printArtist() {
        System.out.printf("%46s | %16s | %14s | %18s\n", "Interpret", "Number of Tracks", "Average Rating", "Single Rating");
        for (Artist a : artistArray) {
            a.printArtist();
        }
    }

    //remove Artist form List
    public static void popArtist(Artist artist) {
        artists.remove(artist.getName(), artist);
        artist = null;
    }

    //sort arraylist by number of songs by artist
    public static void sort1stLevel() {
        preConverter();
        artistArray.sort(Collections.reverseOrder());
    }

    //sort arraylist by mean of ratings
    public static void sort2ndLevel() {
        Artist.RatingComparator sortByRating = new Artist.RatingComparator();
        preConverter();
        artistArray.sort(sortByRating);
    }

    private static void preConverter() {
        if (artistArray == null) {
            artistArray = new ArrayList<>(artists.values());
        }
    }
}
