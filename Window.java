import javax.swing.*;

public class Window extends JFrame {

    public Window() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(new Container());
        setSize(800, 500);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

    }

    public static void main(String[] args) {

        new Window();

    }

}
