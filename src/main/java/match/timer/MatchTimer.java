package match.timer;

import javafx.animation.AnimationTimer;

public class MatchTimer {
    private AnimationTimer timer;
    private long startTime;
    private int lastSecond = -1;
    private boolean paused = false;
    private Runnable onSecondElapsed;

    public MatchTimer(Runnable onSecondElapsed) {
        this.onSecondElapsed = onSecondElapsed;

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int currentSecond = getCurrentTimeSeconds();
                if (currentSecond != lastSecond) {
                    lastSecond = currentSecond;
                    handleOnsecondElapsed();
                }
            }
        };
    }

    public void start() {
        if (paused) {
            paused = false;
            startTime = System.currentTimeMillis() - getCurrentTime();
        } else {
            startTime = System.currentTimeMillis();
        }
        lastSecond = -1; // Reset so the first second fires immediately
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void pause() {
        if (!paused) {
            paused = true;
            timer.stop();
        }
    }

    private void handleOnsecondElapsed() {
        if (onSecondElapsed != null) {
            onSecondElapsed.run();
        }
    }

    public void setOnSecondElapsed(Runnable onSecondElapsed) {
        this.onSecondElapsed = onSecondElapsed;
    }

    public boolean isRunning() {
        return timer != null;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis() - startTime;
    }

    public int getCurrentTimeSeconds() {
        return (int) (getCurrentTime() / 1000);
    }

    public String getCurrentTimeFormatted() {
        int totalSeconds = getCurrentTimeSeconds();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}