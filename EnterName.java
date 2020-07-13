import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnterName extends Sprite implements Runnable {

    private int gameWidth;
    private int gameHeight;
    private Graphics g;

    public EnterName(int gameWidth, int gameHeight, Graphics g) {

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.g = g;

        this.setWidth(0);
        this.setHeight(0);

        this.setX(0);
        this.setY(0);
        //this.setX(gameWidth/2-getWidth()/2);
        //this.setY(gameHeight/2-getHeight()/2);

        this.setxVelocity(2);
        this.setyVelocity(1);

        this.setColor(Color.BLACK);
        this.setEdible(false);

        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                grow();
            }
        }, 0, 50);


    }

    @Override
    public void run() {

        while (true) {

            move();
            boundaryChecking();

            try { Thread.sleep(1); } catch (InterruptedException e) { return; }

        }

    }

    public void boundaryChecking() {

        if (getX() <= 0) { setxVelocity(-getxVelocity());if (getX() <= -3) { setX(getX() + 8); } }
        if (getY() <= 0) { setyVelocity(-getyVelocity());if (getY() <= -3) { setY(getY() + 8); } }
        if (getX() >= gameWidth - getWidth()) { setxVelocity(-getxVelocity()); if (getX() >= getWidth() + 3) { setX(getX() - 8); } }
        if (getY() >= gameHeight - getHeight()) { setyVelocity(-getyVelocity()); if (getY() >= getHeight() + 3) { setY(getY() - 8); } }

    }

    public void grow() {

        if (getHeight() < 400) {
            setWidth(getWidth() + 1);
            setHeight(getHeight() + 1);
        }

    }

}
