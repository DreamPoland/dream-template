package cc.dreamcode.template.nms.api;

import cc.dreamcode.utilities.ClassUtil;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class VersionProvider {
    public static VersionAccessor getVersionAccessor() {

        final String version = VersionProvider.getNmsVersion();
        final String className = "cc.dreamcode.template.nms." + version + "." + version.toUpperCase(Locale.ROOT) + "_VersionAccessor";

        return (VersionAccessor) ClassUtil.getClass(className)
                .map(versionClass -> {
                    try {
                        return versionClass.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException("Could not initialize VersionAccessor for version " + version, e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Cannot find VersionAccessor for version " + version));
    }

    private static String getNmsVersion() {
        final AtomicReference<String> ref = new AtomicReference<>();

        for (Package pack : Package.getPackages()) {
            if (pack.getName().startsWith("org.bukkit.craftbukkit.v")) {
                final String name = pack.getName().split("\\.")[3];

                try {
                    Class.forName("org.bukkit.craftbukkit." + name + ".entity.CraftPlayer");
                    ref.set(name);
                }
                catch (ClassNotFoundException ignored) {}
            }
        }

        final String version = ref.get();
        if (version == null) {
            throw new RuntimeException("Cannot find server version");
        }

        return version;
    }
}
