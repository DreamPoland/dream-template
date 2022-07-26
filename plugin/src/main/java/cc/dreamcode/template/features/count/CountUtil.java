package cc.dreamcode.template.features.count;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CountUtil {
    public static long getCountDown(long start, int timeInSeconds) {
        return (start / 1000) + timeInSeconds - (System.currentTimeMillis() / 1000);
    }

    public static boolean isOut(long start, int timeInSeconds) {
        return getCountDown(start, timeInSeconds) < 0;
    }
}
