import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class GameScreen extends JPanel implements ActionListener {

    private Timer timer = new Timer(0, this);
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private FontHandler fontHandler = new FontHandler();
    private ScoreHandler scoreHandler = new ScoreHandler(0);

    private JTextField tf = new JTextField("");

    private Random rand = new Random();

    private boolean isRunning = false;
    private boolean hasEnded = false;
    //private boolean gettingName = false;

    private Player player;
    private Thread st;

    private Font fontSmall;
    private Font font;
    private Font endFont;

    private int width;
    private int height;
    private CardLayout layout;

    private EnterName en;

    public GameScreen(CardLayout layout) {

        this.layout = layout;

        Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int) toolkit.getWidth() - layout.getHgap() * 2;
        this.height = (int) toolkit.getHeight() - layout.getVgap() * 2;

        setBackground(Color.WHITE);

    }

    @Override
    public void paintComponent(Graphics gr) {

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        //

        for (Sprite s : sprites) {
            g.setColor(s.getColor());
            g.fillOval(s.getX(), s.getY(), s.getWidth(), s.getHeight());
        }

        drawScore(g);

        if (hasEnded) endImage(g);
        //if (gettingName) {
        //    g.setColor(Color.WHITE);
        //    g.drawString("Hello", en.getX()+en.getWidth()/2, en.getY()+en.getHeight()/2);
        //}


        //

        gr.drawImage(bi, 0, 0, this);
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {

        if (!isRunning) setup();

        if (rand.nextInt(100) == 1 && !hasEnded) createObstacle();

        if (!hasEnded && isCollision()) endGame();


        repaint();

    }

    public void setup() {

        isRunning = true;
        hasEnded = false;
        //gettingName = false;

        sprites.clear();
        threads.clear();

        font = fontHandler.registerFont(30f);
        endFont = fontHandler.registerFont(100f);
        fontSmall = fontHandler.registerFont(20f);

        player = new Player(width / 2 - 75 / 2, height / 2 - 75 / 2, width, height);
        Thread p = new Thread(player);
        p.start();
        threads.add(p);
        sprites.add(player);

        scoreHandler.setScore(0);

        st = new Thread(scoreHandler);
        st.start();

        setupKeys();

    }

    public void clearScene() {

        for (Thread t : threads) {
            t.interrupt();
        }

        sprites.clear();
        threads.clear();

    }

    public void createObstacle() {

        if (sprites.size() < 21) {
            Obstacle o = new Obstacle(rand.nextInt(getWidth() - 100), rand.nextInt(getHeight() - 100), width, height);
            Thread ot = new Thread(o);
            ot.start();
            threads.add(ot);
            sprites.add(o);
        }

    }

    public boolean isCollision() {

        Iterator<Sprite> iterator = sprites.iterator();

        while (iterator.hasNext()) {

            Sprite s = iterator.next();

            if (s != player) {

                int a = player.getWidth() / 2 + s.getWidth() / 2;
                int dx = (player.getX() + player.getWidth() / 2) - (s.getX() + s.getWidth() / 2);
                int dy = (player.getY() + player.getHeight() / 2) - (s.getY() + s.getWidth() / 2);
                if (a * a > (dx * dx + dy * dy)) {

                    if (!s.isEdible()) {
                        return true;
                    }

                    else {
                        if (!hasEnded) {
                            if (s.isRed()) {
                                scoreHandler.setScore(scoreHandler.getScore() + 25000);
                            }
                            else {
                                scoreHandler.setScore(scoreHandler.getScore() + 5000);
                                //player.setWidth(player.getWidth() + 1);
                                //player.setHeight(player.getHeight() + 1);
                            }

                            threads.remove(sprites.indexOf(s));
                            iterator.remove();

                        }
                    }

                }

            }

        }

        return false;

    }

    public void endGame() {

        hasEnded = true;
        st.interrupt();
        player.setGameEnded(true);

        ///////

        //gettingName = true;

        //en = new EnterName(width, height, this.getGraphics());
        //Thread et = new Thread(en);
        //et.start();
        //sprites.add(en);

        ///////




        new Highscores();

    }

    public void drawScore(Graphics g) {

        int x = 3;
        int y = 25;

        g.setColor(new Color(0, 0, 0));
        g.setFont(font);
        if (hasEnded) {
            Highscores hs = new Highscores();
            if (hs.playerIsInHighscores(scoreHandler.getScore())) {
                g.drawString("New Highscore!", x, y + 100);
            }
            g.setFont(endFont); y = 80;
        }
        g.drawString(Integer.toString(scoreHandler.getScore()), x, y);

    }

    public void endImage(Graphics g) {

        String text = "Press 'R' to Restart or 'H' to view Highscores!";

        g.setFont(fontSmall);
        g.setColor(new Color(0, 0, 0));
        g.drawString(text, 3, this.height-5);

    }

    public void drawEnterName(Graphics g) {

        g.drawString("Hello", en.getX(), en.getY());

    }

    public void viewHighscores() {

        layout.show(getParent(), "es");

    }

    public void setupKeys() {

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "LEFT");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "RIGHT");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "UP");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "DOWN");

        ///////////////////
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LEFT");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RIGHT");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UP");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DOWN");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "LEFTREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "RIGHTREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "UPREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "DOWNREL");
        ///////////////////


        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "LEFTREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "RIGHTREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "UPREL");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "DOWNREL");

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, false), "RESTART");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "ENDGAME");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0, false), "HIGHSCORES");

        this.getActionMap().put("LEFT", player.left);
        this.getActionMap().put("RIGHT", player.right);
        this.getActionMap().put("UP", player.up);
        this.getActionMap().put("DOWN", player.down);
        this.getActionMap().put("LEFTREL", player.leftrel);
        this.getActionMap().put("RIGHTREL", player.rightrel);
        this.getActionMap().put("UPREL", player.uprel);
        this.getActionMap().put("DOWNREL", player.downrel);

        this.getActionMap().put("LEFT", player.left);
        this.getActionMap().put("RIGHT", player.right);
        this.getActionMap().put("UP", player.up);
        this.getActionMap().put("DOWN", player.down);
        this.getActionMap().put("LEFTREL", player.leftrel);
        this.getActionMap().put("RIGHTREL", player.rightrel);
        this.getActionMap().put("UPREL", player.uprel);
        this.getActionMap().put("DOWNREL", player.downrel);

        this.getActionMap().put("RESTART", restart);
        this.getActionMap().put("ENDGAME", endGame);
        this.getActionMap().put("HIGHSCORES", highscores);

    }

    public Action restart = new AbstractAction("Restart") {
        public void actionPerformed(ActionEvent e) {
            clearScene();
            setup();
        }
    };

    public Action endGame = new AbstractAction("EndGame") {
        public void actionPerformed(ActionEvent e) {
            endGame();
        }
    };

    public Action highscores = new AbstractAction("Highscores") {
        public void actionPerformed(ActionEvent e) {
            viewHighscores();
        }
    };

}
