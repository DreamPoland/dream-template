package cc.dreamcode.template;

import cc.dreamcode.template.exception.PluginRuntimeException;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PluginBootLoader extends JavaPlugin {

    @Getter private final AtomicBoolean pluginDisabled = new AtomicBoolean(false);

    @Override
    public void onLoad() {
        try {
            this.load();
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when plugin are loading...", e, this);
        }
    }

    @Override
    public void onEnable() {
        if (this.getPluginDisabled().get()) {
            return;
        }

        try {
            this.start();
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when plugin are starting...", e, this);
        }
    }

    @Override
    public void onDisable() {
        if (this.getPluginDisabled().get()) {
            return;
        }

        try {
            this.stop();
        }
        catch (Exception e) {
            throw new PluginRuntimeException("An error was caught when plugin are stopping...", e);
        }
    }

    public abstract void load();

    public abstract void start();

    public abstract void stop();

}
