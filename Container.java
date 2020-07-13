import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Container extends JPanel {

    private int hgap = 40;
    private int vgap = 40;

    private CardLayout layout = new CardLayout(hgap, vgap);

    private MainMenu mm = new MainMenu(layout);
    private GameScreen gs = new GameScreen(layout);
    private EndScreen es = new EndScreen(layout);
    TestPanel test = new TestPanel();


    public Container() {

        setLayout(layout);
        setBackground(Color.BLACK);

        add(mm, "mm");
        add(gs, "gs");
        add(es, "es");
        add(test, "test");

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "QUIT");

        Action quit = new AbstractAction("Quit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        this.getActionMap().put("QUIT", quit);




    }

}
