package cc.dreamcode.template.boot;

import cc.dreamcode.template.exception.PluginRuntimeException;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.concurrent.atomic.AtomicBoolean;

public final class PluginFactory {

    @Deprecated
    public static boolean checkPlugin(final AtomicBoolean atomicBoolean, final PluginDescriptionFile pluginDescriptionFile) {
        if (!pluginDescriptionFile.getRawName().equals("Dream-Template")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin name is changed...");
            return false;
        }
        if (!pluginDescriptionFile.getAuthors().contains("Ravis96")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin authors is changed...");
            return false;
        }
        if (pluginDescriptionFile.getDescription() != null &&
                !pluginDescriptionFile.getDescription().equals("Template plugin by DreamCode.")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin description is changed...");
            return false;
        }
        if (pluginDescriptionFile.getWebsite() != null &&
                !pluginDescriptionFile.getWebsite().equals("DreamCode - https://discord.gg/dreamcode")) {
            atomicBoolean.set(true);
            throwRuntimeException("Provided plugin website is changed...");
            return false;
        }
        return true;
    }

    private static void throwRuntimeException(String name) {
        throw new PluginRuntimeException(name);
    }

}
