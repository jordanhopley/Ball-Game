public class ScoreHandler implements Runnable {

    private int score = 0;

    public ScoreHandler(int startingScore) {

        setScore(startingScore);

    }

    @Override
    public void run() {

        while (true) {

            try { Thread.sleep(10); } catch (InterruptedException e) { return; }
            setScore(getScore() + 10);

        }

    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}