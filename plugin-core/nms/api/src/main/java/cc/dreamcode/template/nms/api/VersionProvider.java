package cc.dreamcode.template.nms.api;

import cc.dreamcode.utilities.ClassUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@UtilityClass
public class VersionProvider {

    private final TreeMap<String, String> NEWER_NMS_VERSION = new TreeMap<>(MapBuilder.of(
            "1.20.6", "v1_20_R5",
            "1.21", "v1_21_R1",
            "1.21.1", "v1_21_R1",
            "1.21.2", "v1_21_R2",
            "1.21.3", "v1_21_R2",
            "1.21.4", "v1_21_R3"
    ));

    public static VersionAccessor getVersionAccessor() {

        final String version = getNmsVersion();
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

        final String[] nmsVersionSplit = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        if (nmsVersionSplit.length >= 4) {
            final String nmsVersion = nmsVersionSplit[3];
            if (nmsVersion.startsWith("v")) {
                return nmsVersion;
            }
        }

        final String dataVersion = Bukkit.getBukkitVersion().split("-")[0];
        Map.Entry<String, String> entry = NEWER_NMS_VERSION.floorEntry(dataVersion);

        if (entry == null) {
            throw new RuntimeException("Cannot find server version id:" + dataVersion + " (" + Bukkit.getVersion() + ")");
        }

        return entry.getValue();
    }
}
