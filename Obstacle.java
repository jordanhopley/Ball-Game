import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Obstacle extends Sprite implements Runnable {

    private Random rand = new Random();

    private boolean canGrow = false;
    private boolean canChangeDirection = true;

    public Obstacle(int x, int y, int gameWidth, int gameHeight) {

        this.setX(x);
        this.setY(y);

        int size = 25;
        this.setWidth(size);
        this.setHeight(size);

        this.setEdible(true);

        this.setColor(new Color(255, 216, 24));

        this.setSpeed(8);
        this.setxVelocity(getRandomVelocity(getSpeed()));
        this.setyVelocity(getRandomVelocity(getSpeed()));

        this.setGameWidth(gameWidth);
        this.setGameHeight(gameHeight);

        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (canChangeDirection)
                    changeDirection();
            }
        }, 1000, 2000);

        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                canChangeDirection = false;

                if (getxVelocity() > 0) setxVelocity(2);
                if (getxVelocity() < 0) setxVelocity(-2);
                if (getyVelocity() > 0) setyVelocity(2);
                if (getyVelocity() < 0) setyVelocity(-2);

                setRed(false);
                setColor(Color.BLACK);
                canGrow = true;
            }
        }, 5000);

        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                setColor(new Color(235, 0, 3));
                setRed(true);
            }
        }, 4000);

    }

    @Override
    public void run() {

        while (true) {

            move();
            boundaryChecking();

            if (canGrow) { grow(); setEdible(false); }

            try { Thread.sleep(10); } catch (InterruptedException e) { return; }


        }

    }

    public int getRandomVelocity(int bound) {

        int num = 0;
        while (num == 0) {
            num = rand.nextInt(bound+1)-bound/2;
        }
        return num;

    }

    public void changeDirection() {

        setxVelocity(getRandomVelocity(getSpeed()));
        setyVelocity(getRandomVelocity(getSpeed()));

    }

    public void grow() {

        if (getHeight() < 100) {
            setWidth(getWidth() + 1);
            setHeight(getHeight() + 1);
            setColor(getColor().darker());
        } else {
            canGrow = false;
            setGrown(true);
        }

    }

    public void boundaryChecking() {

        if (getX() <= 0) { setxVelocity(-getxVelocity());if (getX() <= -3) { setX(getX() + 7); } }
        if (getY() <= 0) { setyVelocity(-getyVelocity());if (getY() <= -3) { setY(getY() + 7); } }
        if (getX() >= getGameWidth() - getWidth()) { setxVelocity(-getxVelocity()); if (getX() >= getWidth() + 3) { setX(getX() - 7); } }
        if (getY() >= getGameHeight() - getHeight()) { setyVelocity(-getyVelocity()); if (getY() >= getHeight() + 3) { setY(getY() - 7); } }

    }

}
