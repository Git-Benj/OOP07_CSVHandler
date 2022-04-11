/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 10.04.2022
 */

public class Application {

    public static void main(String[] args) {

        Tracks top40 = new Tracks("input/music2022.csv");

        //prints all tracks to console
        top40.printList();

        //start of new week
        top40.shiftWeek();
        //new song put in list
        top40.putNewTrack(8, new Track("Tanz der Kühe", "Lisa Stoll", 1.5f));

        //print CSV
        IOHandler.createCSV(top40.getTracks(), "KW2");

        //artists are sorted by numbers of songs in list
        Artists.sort1stLevel();
        //write artists to CSV
        IOHandler.createCSV(Artists.artistArray, "sortLevel1");

        //artists are sorted by mean of ratings
        Artists.sort2ndLevel();
        //write artists to CSV
        IOHandler.createCSV(Artists.artistArray, "sortLevel2");

        //print artists into console
        Artists.printArtist();

    }

}