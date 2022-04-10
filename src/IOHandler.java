/*
 * Project: "Top40"
 * Author: Benjamin Lamprecht
 * Created: 09.04.2022
 * Last Change: 09.04.2022
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IOHandler {

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

    public static void createCSV(HashMap<Integer, Track> top40s, String year) {
        String filePath = "output/" + "music" + year + ".csv";
        String line;
        File file = new File(filePath);
        for (int i = 1; true; i++) {
            if (!file.exists() || file.canWrite()) {
                break;
            }
            filePath = filePath.replace(".csv", "V" + i + ".csv");
            file = new File(filePath);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            Track t;
            for (int i = 0; i <= top40s.size(); i++) {
                if (i < 1) {
                    line = "DW;LW;WW;Titel;Interpret;Bewertung\n";
                } else {
                    t = top40s.get(i);
                    line = String.valueOf(i) + ';' + t.getLw() + ';' + t.getWw() +
                            ';' + t.getTitle() + ';' + t.getArtist() + ';' +
                            String.valueOf(t.getRating()).replace('.', ',') + '\n';
                }
                bw.write(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
