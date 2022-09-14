package cc.dreamcode.template.features.command;

import cc.dreamcode.template.exception.PluginValidatorException;
import cc.dreamcode.template.features.notice.Notice;

import java.util.Map;
import java.util.Optional;

/**
 * Validator for checking simple question and answer when false to command sender.
 */
public interface CommandValidator {

    default <T> T whenNull(T t, Notice notice) {
        if (t == null) {
            throw new PluginValidatorException(notice);
        }

        return t;
    }

    default <T> T whenNull(T t, Notice notice, Map<String, Object> replaceMap) {
        if (t == null) {
            throw new PluginValidatorException(notice, replaceMap);
        }

        return t;
    }

    default void when(boolean check, Notice notice) {
        if (check) {
            throw new PluginValidatorException(notice);
        }
    }

    default void when(boolean check, Notice notice, Map<String, Object> replaceMap) {
        if (check) {
            throw new PluginValidatorException(notice, replaceMap);
        }
    }

    default <T> T when(Optional<T> optional, Notice notice) {
        return optional.orElseThrow(() -> new PluginValidatorException(notice));
    }

    default <T> T when(Optional<T> optional, Notice notice, Map<String, Object> replaceMap) {
        return optional.orElseThrow(() -> new PluginValidatorException(notice, replaceMap));
    }

    default void whenNot(boolean check, Notice notice) {
        if (!check) {
            throw new PluginValidatorException(notice);
        }
    }

    default void whenNot(boolean check, Notice notice, Map<String, Object> replaceMap) {
        if (!check) {
            throw new PluginValidatorException(notice, replaceMap);
        }
    }
}
