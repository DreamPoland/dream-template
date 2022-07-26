package cc.dreamcode.template.exception;

import org.bukkit.plugin.Plugin;

public class PluginNullPointerException extends NullPointerException {

    public PluginNullPointerException(String text) {
        super(text);
    }

    public PluginNullPointerException(String text, Plugin pluginToDisable) {
        super(text);
        pluginToDisable.getServer().getPluginManager().disablePlugin(pluginToDisable);
    }

}
