package cc.dreamcode.template.exception;

import cc.dreamcode.template.features.notice.Notice;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class PluginValidatorException extends RuntimeException {

    @Getter private final Notice notice;
    @Getter private Map<String, Object> replaceMap = new HashMap<>();

    public PluginValidatorException(Notice notice) {
        this.notice = notice;
    }

    public PluginValidatorException(Notice notice, Map<String, Object> replaceMap) {
        this.notice = notice;
        this.replaceMap = replaceMap;
    }

}
