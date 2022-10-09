package cc.dreamcode.template.utilities;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ParseUtil {
    public static Optional<Integer> parseInteger(@NonNull String arg) {
        try {
            int i = Integer.parseInt(arg);
            return Optional.of(i);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Double> parseDouble(@NonNull String arg) {
        try {
            double d = Double.parseDouble(arg);
            return Optional.of(d);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
