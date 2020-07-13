import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Player extends Sprite implements Runnable {

    private boolean isLeft, isRight, isUp, isDown;

    public Player(int x, int y, int gameWidth, int gameHeight) {

        this.setX(x);
        this.setY(y);
        this.setxVelocity(0);
        this.setyVelocity(0);
        this.setColor(new Color(225, 48, 52));
        this.setWidth(75);
        this.setHeight(75);
        this.setSpeed(4);

        this.setGameWidth(gameWidth);
        this.setGameHeight(gameHeight);

    }

    @Override
    public void run() {

        int waitTime = 4;

        while (true) {

            if (gameEnded()) {
                setWidth(getWidth() - 1);
                setHeight(getHeight() - 1);
                waitTime = 10; }

            move();
            boundaryChecking();
            try { Thread.sleep(waitTime); } catch (InterruptedException e) { return; }

        }

    }

    public void boundaryChecking() {

        if (getX() < 0) { setX(0); }

        if (getY() < 0) { setY(0); }

        if (getX() > getGameWidth() - getWidth()) { setX(getGameWidth() - getWidth()); }

        if (getY() > getGameHeight() - getHeight()) { setY(getGameHeight() - getHeight()); }

    }

    public void update() {

        setxVelocity(0);
        setyVelocity(0);

        if (isLeft) setxVelocity(-getSpeed());
        if (isRight) setxVelocity(getSpeed());
        if (isUp) setyVelocity(-getSpeed());
        if (isDown) setyVelocity(getSpeed());

    }

    public Action left = new AbstractAction("Left") {
        public void actionPerformed(ActionEvent e) {
            isLeft = true;
            update();
        }
    };

    public Action right = new AbstractAction("Right") {
        public void actionPerformed(ActionEvent e) {
            isRight = true;
            update();
        }
    };

    public Action up = new AbstractAction("Up") {
        public void actionPerformed(ActionEvent e) {
            isUp = true;
            update();
        }
    };

    public Action down = new AbstractAction("Down") {
        public void actionPerformed(ActionEvent e) {
            isDown = true;
            update();
        }
    };

    public Action leftrel = new AbstractAction("Leftrel") {
        public void actionPerformed(ActionEvent e) {
            isLeft = false;
            update();
        }
    };

    public Action rightrel = new AbstractAction("Rightrel") {
        public void actionPerformed(ActionEvent e) {
            isRight = false;
            update();
        }
    };

    public Action uprel = new AbstractAction("Uprel") {
        public void actionPerformed(ActionEvent e) {
            isUp = false;
            update();
        }
    };

    public Action downrel = new AbstractAction("Downrel") {
        public void actionPerformed(ActionEvent e) {
            isDown = false;
            update();
        }
    };

}
