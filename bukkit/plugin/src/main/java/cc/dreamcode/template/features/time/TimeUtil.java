package cc.dreamcode.template.features.time;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUtil {
    public static String convertLong(long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (days * 24 * 60) - (hours * 60);
        long secs = seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        if (days != 0) {
            return days + "d " + hours + "h " + minutes + "min " + secs + "s";
        }
        if (hours != 0) {
            return hours + "h " + minutes + "min " + secs + "s";
        }
        if (minutes != 0) {
            return minutes + "min " + secs + "s";
        }
        return secs + "s";
    }
}
