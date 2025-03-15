package utils;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class TimerUtil {
    long start;
    public TimerUtil() {
        start = System.nanoTime();
    }

    public void end() {
        System.out.print("passing by: ");
        System.out.println(NANOSECONDS.toMillis(System.nanoTime() - start) + "ms");
    }

    public void restart() {
        start = System.nanoTime();
    }
}
