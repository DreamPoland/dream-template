package cc.dreamcode.template.features.optional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Additional optional methods for java 8.
 */
@SuppressWarnings("ALL")
@RequiredArgsConstructor
public class CustomOptional<T> {

    private final Optional<T> optional;

    public static <T> CustomOptional<T> of(Optional<T> optional) {
        return new CustomOptional<>(optional);
    }

    public void ifPresentOrElse(Consumer<T> target, Runnable runnable) {
        if (this.optional.isPresent()) {
            this.optional.ifPresent(target);
        }
        else {
            runnable.run();
        }
    }
}
