/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 10.04.2022
 */

import java.util.ArrayList;

public class Track {
    private short lw;
    private short ww;
    private final String title;
    private final ArrayList<Artist> artist;
    private final float rating;

    //constructor based on csv entry, if two artists are on 1 track then rating applies to 2 artists
    public Track(short lw, short ww, String title, String artist, float rating) {
        this.lw = lw;
        this.ww = ww;
        this.title = title;
        this.artist = new ArrayList<>();
        String[] li = artist.split(" , ");
        for (int i = 0; i < li.length; i++) {
            this.artist.add(getInstance(li[i]));
            this.artist.get(i).putRatings(rating);
        }
        this.rating = rating;
    }

    //constructor for new tracks
    public Track(String title, String artist, float rating) {
        this.ww = 1;
        this.title = title;
        this.artist = new ArrayList<>();
        String[] li = artist.split(" , ");
        for (int i = 0; i < li.length; i++) {
            this.artist.add(getInstance(li[i]));
            this.artist.get(i).putRatings(rating);
        }
        this.rating = rating;
    }

    //searches for listed Artist by name, else creates new Artist and puts it in list(artists)
    public static Artist getInstance(String str) {
        if (Artists.artists.containsKey(str)) {
            return Artists.artists.get(str);
        }
        return new Artist(str);
    }

    public short getLw() {
        return lw;
    }

    public short getWw() {
        return ww;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        StringBuilder out = new StringBuilder();
        for (Artist a : artist) {
            out.append(a.getName()).append(" / ");
        }
        out.setLength(out.length() - 3);
        return out.toString();
    }

    public ArrayList<Artist> getArtistList() {
        return artist;
    }

    public float getRating() {
        return rating;
    }

    public void setLw(short lw) {
        this.lw = lw;
    }

    public void setWw() {
        this.ww++;
    }

    //print into console
    public void printTrack(int i) {
        System.out.printf("%3d | %3d | %3d | %32s | %46s | %3.1f\n", i, lw, ww, title, getArtist(), rating);
    }
}
