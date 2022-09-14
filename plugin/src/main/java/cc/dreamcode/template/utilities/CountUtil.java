package cc.dreamcode.template.utilities;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class CountUtil {
    public static long getCountDownSeconds(long start, int timeInSeconds) {
        return (start / 1000) + timeInSeconds - (System.currentTimeMillis() / 1000);
    }

    public static long getCountDownSeconds(long start, Duration duration) {
        return (start / 1000) + duration.getSeconds() - (System.currentTimeMillis() / 1000);
    }

    public static long getCountDownMills(long start, long timeInMills) {
        return start + timeInMills - System.currentTimeMillis();
    }

    public static long getCountDownMills(long start, Duration duration) {
        return start + duration.toMillis() - System.currentTimeMillis();
    }

    public static boolean isOut(long start, int timeInSeconds) {
        return getCountDownSeconds(start, timeInSeconds) < 0;
    }

    public static boolean isOut(long start, long timeInMills) {
        return getCountDownMills(start, timeInMills) < 0;
    }

    public static boolean isOut(long start, @NonNull Duration duration) {
        return getCountDownMills(start, duration.toMillis()) < 0;
    }
}
