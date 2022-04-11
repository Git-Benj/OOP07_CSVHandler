/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 10.04.2022
 * Last Change: 10.04.2022
 */

import java.util.ArrayList;
import java.util.Comparator;

public class Artist implements Comparable<Artist> {
    private final String name;
    private float ratingMean = 0;
    private final ArrayList<Float> ratings;

    //constructor puts new artist in artists map
    public Artist(String name) {
        this.name = name;
        ratings = new ArrayList<>();
        Artists.artists.put(name, this);
    }

    //gives current mean of ratings based on amount  of ratings
    public float getRatingMean() {
        return ratingMean;
    }

    //recalculates mean of ratings
    public void setRatingMean() {
        ratingMean = (float) ratings.stream().mapToDouble(Float::doubleValue).sum();
        ratingMean = Math.round((ratingMean / ratings.size()) * 100f) / 100f;
    }

    //adds value to mean and adds value to array of ratings
    public void putRatings(float rating) {
        ratingMean = 0;
        ratings.add(rating);
        setRatingMean();
    }

    //removes Rating from Artist, if there is no Rating then the artist will be removed
    public void popRatings(float rating) {
        ratings.remove(rating);
        if (ratings.isEmpty()) {
            Artists.popArtist(this);
        } else {
            setRatingMean();
        }
    }

    public String getName() {
        return name;
    }

    public String getRatings() {
        StringBuilder out = new StringBuilder();
        for (float f : ratings) {
            out.append(f).append(", ");
        }
        out.setLength(out.length() - 2);
        return out.toString();
    }

    //Print Artist
    public void printArtist() {
        System.out.printf("%46s | %16d | %14.2f | %18s\n", name, ratings.size(), getRatingMean(), getRatings());
    }

    //compare by amount of tracks by artist
    @Override
    public int compareTo(Artist o) {
        return this.ratings.size() - o.ratings.size();
    }

    //compare by mean of ratings
    public static class RatingComparator implements Comparator<Artist> {
        public int compare(Artist o1, Artist o2) {
            return Float.compare(o2.getRatingMean(), o1.getRatingMean());
        }
    }


}
