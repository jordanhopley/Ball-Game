import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EndScreen extends JPanel {

    private JLabel scoreLabel = new JLabel();
    private JLabel nameLabel = new JLabel();

    private JTextField scoreTextField = new JTextField();

    private Highscores hs = new Highscores();
    private FontHandler fh = new FontHandler();

    private List<String> highscores = new ArrayList<>();

    private CardLayout layout;
    private Font font;

    public EndScreen(CardLayout layout) {

        this.layout = layout;

        setLayout(new GridLayout(1, 2));
        setBackground(Color.WHITE);
        font = fh.registerFont(50f);

        highscores = hs.readHighscores();

        printNames();
        printScores();

        add(nameLabel);
        add(scoreLabel);

    }

    public void printScores() {

        String text = "<html>";

        for (String h : highscores) {

            text += "#####" + h.split(" ")[1] + "<br>";

        }

        text += "</html>";

        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(font);
        scoreLabel.setText(text);

    }

    public void printNames() {

        String text = "<html>";
        int i = 0;

        for (String h : highscores) {

            text += "#" + Integer.toString(i+1) + ".#" + h.split(" ")[0] + "<br>";
            i++;

        }

        text += "</html>";

        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(font);
        nameLabel.setText(text);

    }

}
