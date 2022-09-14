package cc.dreamcode.template.utilities;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUtil {
    public static String convertSeconds(long seconds) {
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

    public static String convertMills(long milliseconds) {
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) - (days * 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - (days * 24 * 60) - (hours * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        long millisecondsFinal = milliseconds - (days * 24 * 60 * 60 * 1000)  - (hours * 60 * 60 * 1000) - (minutes * 60 * 1000) - (seconds * 1000);

        if (millisecondsFinal == 1000) {
            millisecondsFinal = millisecondsFinal - 1;
        }

        if (days != 0) {
            return days + "d " + hours + "h " + minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        if (hours != 0) {
            return hours + "h " + minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        if (minutes != 0) {
            return minutes + "min " + seconds + "s " + millisecondsFinal + "ms";
        }
        if (seconds != 0) {
            return seconds + "s " + millisecondsFinal + "ms";
        }

        return millisecondsFinal + "ms";
    }

    public static String convertDurationSeconds(@NonNull Duration duration) {
        return convertSeconds(duration.getSeconds());
    }

    public static String convertDurationMills(@NonNull Duration duration) {
        return convertMills(duration.toMillis());
    }

}
