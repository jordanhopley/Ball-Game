import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {

    private Color backgroundColor = new Color(255, 255, 255);

    private Button start = new Button(backgroundColor, Color.BLACK, "Start", 100f, 130f);
    private Button highscores = new Button(backgroundColor, Color.BLACK, "Highscores", 100f, 130f);
    private Button quit = new Button(backgroundColor, Color.BLACK, "Quit", 100f, 130f);

    private CardLayout cardLayout;

    public MainMenu(CardLayout cardLayout) {

        this.cardLayout = cardLayout;

        setLayout(new GridLayout(3, 1));
        setBackground(backgroundColor);

        start.addActionListener(this);
        highscores.addActionListener(this);
        quit.addActionListener(this);

        add(start);
        add(highscores);
        add(quit);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == start) {
            cardLayout.show(getParent(), "gs");
        }

        if (e.getSource() == highscores) {
            //cardLayout.show(getParent(), "es");
        }

        if (e.getSource() == quit) {
            System.exit(0);
        }

    }

}
