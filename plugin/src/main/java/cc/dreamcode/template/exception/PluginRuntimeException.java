package cc.dreamcode.template.exception;

import lombok.NoArgsConstructor;
import org.bukkit.plugin.Plugin;

@NoArgsConstructor
public class PluginRuntimeException extends RuntimeException {

    public PluginRuntimeException(String text) {
        super(text);
    }

    public PluginRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public PluginRuntimeException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public PluginRuntimeException(String text, Plugin pluginToDisable) {
        super(text);
        pluginToDisable.getServer().getPluginManager().disablePlugin(pluginToDisable);
    }

    public PluginRuntimeException(Throwable throwable, Plugin pluginToDisable) {
        super(throwable);
        pluginToDisable.getServer().getPluginManager().disablePlugin(pluginToDisable);
    }

    public PluginRuntimeException(String text, Throwable throwable, Plugin pluginToDisable) {
        super(text, throwable);
        pluginToDisable.getServer().getPluginManager().disablePlugin(pluginToDisable);
    }

}
