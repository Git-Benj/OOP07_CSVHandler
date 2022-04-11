/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 10.04.2022
 */

import java.util.HashMap;
import java.util.List;

public class Tracks {
    private final HashMap<Integer, Track> tracks = new HashMap<>();

    //fetches via IOHandler the inputs of a Track.csv and parses its values for track constructor
    public Tracks(String input) {
        short lw = 0;
        short dw = 0;
        float rating = 0;
        List<List> list = IOHandler.readCSV(input);
        for (List ls : list.subList(1, list.size())) {
            tracks.put((int) IOHandler.parseShort(ls, 0), new Track(IOHandler.parseShort(ls, 1), IOHandler.parseShort(ls, 2), (String) ls.get(3), (String) ls.get(4), IOHandler.parseFloat(ls, 5)));
        }
    }

    //write exception into logFile.txt
    private void cellError(String s, List ls, Exception e) {
        IOHandler.writingLOG(s + " @ line:" + ls + " --ignoring\n(" + e.getMessage() + ")");
    }

    //rearranging list with new track
    public void putNewTrack(int ranking, Track track) {
        Track nTrack = tracks.get(ranking);
        tracks.put(ranking, track);
        if (ranking <= 75) {
            putNewTrack(ranking + 1, nTrack);
        } else {
            popOldTrack(track);
        }
    }

    //garbage collection for old track
    public void popOldTrack(Track track) {
        for (Artist a : track.getArtistList()) {
            a.popRatings(track.getRating());
        }
        tracks.remove(76, track);
    }

    //DW and LW swaps
    public void shiftWeek() {
        for (int i = 1; i <= tracks.size(); i++) {
            tracks.get(i).setLw((short) i);
            tracks.get(i).setWw();
        }
    }


    public void printList() {
        System.out.printf("%3s | %3s | %3s | %32s | %46s | %3s\n", "DW", "LW", "WW", "Titel", "Interpret", "Bewertung");
        for (int i = 1; i <= tracks.size(); i++) {
            if (tracks.containsKey(i)) {
                tracks.get(i).printTrack(i);
            }
        }
    }

    public HashMap<Integer, Track> getTracks() {
        return tracks;
    }
}
