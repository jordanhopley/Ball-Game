import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Highscores {

    private final String FILEPATH = "src\\Highscores";
    private List<String> highscores = new ArrayList<>();

    public Highscores() {

        addToHighscores(8, "Test");

    }

    public void addToHighscores(int score, String name) {

        try {

            readHighscores();

            String toAdd = Integer.toString(score).concat(" ".concat(name));
            highscores.add(getPosition(score), toAdd);
            highscores.remove(10);

            writeToHighscores(name, score);

        } catch (IndexOutOfBoundsException e) { return; }

    }

    public List<String> readHighscores() {

        try {

            BufferedReader br = Files.newBufferedReader(Paths.get(FILEPATH));
            highscores = br.lines().collect(Collectors.toList());
            return highscores;

        } catch (IOException e) { e.printStackTrace(); }

        return highscores;

    }

    private void writeToHighscores(String name, int score) {

        try {

            BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILEPATH));

            for (String h : highscores) {
                bw.write(h);
                bw.newLine();
            }

            bw.flush();

        } catch ( IOException e ) { e.printStackTrace(); }

    }

    public int getPosition(int score) {

        int test;

        for (String h  : highscores) {

            test = Integer.parseInt(h.split(" ")[1]);
            if (score >= test) {
                return (highscores.indexOf(h));
            }


        }

        return -1;

    }

    public boolean playerIsInHighscores(int score) {

        return (getPosition(score) != -1);

    }

    public List<String> getHighscores() {
        return highscores;
    }


}
