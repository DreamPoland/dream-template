package cc.dreamcode.template.features.validation;

import cc.dreamcode.template.exception.PluginValidationException;
import cc.dreamcode.template.features.notice.Notice;

import java.util.Map;
import java.util.Optional;

public interface ValidationService {

    default <T> T whenNull(T t, Notice notice) {
        if (t == null) {
            throw new PluginValidationException(notice);
        }

        return t;
    }

    default <T> T whenNull(T t, Notice notice, Map<String, Object> replaceMap) {
        if (t == null) {
            throw new PluginValidationException(notice, replaceMap);
        }

        return t;
    }

    default void when(boolean check, Notice notice) {
        if (check) {
            throw new PluginValidationException(notice);
        }
    }

    default void when(boolean check, Notice notice, Map<String, Object> replaceMap) {
        if (check) {
            throw new PluginValidationException(notice, replaceMap);
        }
    }

    default <T> T when(Optional<T> optional, Notice notice) {
        return optional.orElseThrow(() -> new PluginValidationException(notice));
    }

    default <T> T when(Optional<T> optional, Notice notice, Map<String, Object> replaceMap) {
        return optional.orElseThrow(() -> new PluginValidationException(notice, replaceMap));
    }

    default void whenNot(boolean check, Notice notice) {
        if (!check) {
            throw new PluginValidationException(notice);
        }
    }

    default void whenNot(boolean check, Notice notice, Map<String, Object> replaceMap) {
        if (!check) {
            throw new PluginValidationException(notice, replaceMap);
        }
    }
}
