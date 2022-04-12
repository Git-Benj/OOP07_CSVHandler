/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 11.04.2022
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class IOHandler {

    //log.File.txt is deleted and CSV file values put into ArrayList with list elements(equal to cell values)
    public static ArrayList<List> readCSV(String filePath) {
        new File("output/logFile.txt").delete();
        List<String> attributes;
        ArrayList<List> out = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                attributes = Arrays.asList(line.split(";"));
                if (attributes.size() < 6) {
                    writingLOG("Malformed input @ line: " + line + "--ignoring line");
                } else {
                    out.add(attributes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    //return short value from list object
    public static Short parseShort(List<String> ls, int pos) {
        String s = "";
        try {
            if (!Objects.equals(ls.get(pos), "")) {
                return Short.parseShort(ls.get(pos));
            } else {
                return 0;
            }
        } catch (Exception e) {
            switch (pos) {
                case 0 -> s = "Number parsing error for DW";
                case 1 -> s = "Number parsing error for LW";
                case 2 -> s = "Number parsing error for WW";
            }
            IOHandler.writingLOG(s + " @ line:" + ls + " --ignoring\n(" + e.getMessage() + ")");
        }
        return -1;
    }

    //return float value from list object
    public static Float parseFloat(List<String> ls, int pos) {
        try {
            if (!Objects.equals(ls.get(pos), "")) {
                return Float.parseFloat(ls.get(pos).replace(',', '.'));
            } else {
                return 0f;
            }
        } catch (Exception e) {
            IOHandler.writingLOG("Parse exception for Bewertung @ line:" + ls + " --ignoring\n(" + e.getMessage() + ")");
        }
        return -1f;
    }

    //writes tracks handled by program into a new CSV
    public static void createCSV(HashMap<Integer, Track> tracks, String week) {
        StringBuilder filePath = new StringBuilder();
        File file = new File(filePath.append("output/music").append(week).append(".csv").toString());
        StringBuilder line = new StringBuilder();
        for (int j = 2; true; j++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                Track t;
                bw.write("DW;LW;WW;Titel;Interpret;Bewertung\n");
                for (int i = 1; i <= tracks.size(); i++) {
                    t = tracks.get(i);
                    line.append(i).append(';')
                            .append(appendNum(t.getLw())).append(';')
                            .append(appendNum(t.getWw())).append(';')
                            .append(t.getTitle()).append(';')
                            .append(t.getArtist()).append(';')
                            .append(String.valueOf(t.getRating()).replace('.', ',')).append('\n');
                    bw.write(line.toString());
                    line.setLength(0);
                }
                return;
            } catch (Exception e) {
                file = new File(file.getPath().replace(".csv", "_V" + j + ".csv"));
            }
        }
    }

    public static String appendNum(short s) {
        if (s == 0) {
            return "";
        }
        return String.valueOf(s);
    }

    //writes artists in CSV. ATTENTION it has to be sorted before written in CSV
    public static void createCSV(ArrayList<Artist> artists, String sortLevel) {
        File file = new File("output/artists_" + sortLevel + ".csv");
        StringBuilder line = new StringBuilder();
        for (int i = 0; true; i++) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Interpret;Number of Tracks;Average Rating;Single Rating\n");
                for (Artist a : artists) {
                    line.append(a.getName()).append(';')
                            .append(a.getRatings().length()).append(';')
                            .append(a.getRatingMean()).append(';')
                            .append(a.getRatings()).append('\n');
                    bw.write(line.toString());
                    line.setLength(0);
                }
                return;
            } catch (IOException e) {
                file = new File(file.getPath().replace(".csv", "_V" + i + ".csv"));
            }
        }
    }

    //finds/creates a file based on writing permission
    public static File fileIsWritable(File file) {
        if (file.exists() && !Files.isWritable(Path.of(file.getAbsolutePath()))) {
            for (int i = 2; true; i++) {
                file = new File(file.getPath().replace(".csv", "_V" + i + ".csv"));
                break;
            }
        }
        return file;
    }

    //creates logFile of Exceptions and adds lines
    public static void writingLOG(String message) {
        File file = new File("output/logFile.txt");
        try (FileWriter fw = new FileWriter(file, true)) {
            if (file.exists()) {
                fw.write(message);
                fw.write('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
