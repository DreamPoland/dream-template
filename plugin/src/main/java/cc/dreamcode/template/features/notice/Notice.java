package cc.dreamcode.template.features.notice;

import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Notice {

    private final Type type;
    private final String text;
    private int duration = 70;

    public Notice(Type type, String... texts) {
        this.type = type;

        final StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(texts).forEach(text ->
                stringBuilder.append(text).append(lineSeparator()));
        this.text = stringBuilder.toString();
    }

    public enum Type {
        CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR
    }

    public String getColoredText() {
        return ChatColor.translateAlternateColorCodes('&', this.text);
    }

    public PlaceholderContext getContext() {
        return PlaceholderContext.of(CompiledMessage.of(this.getColoredText()));
    }

    public PlaceholderContext getRawContext() {
        return PlaceholderContext.of(CompiledMessage.of(this.text));
    }

    public static String lineSeparator() {
        return "%NEWLINE%";
    }

}
