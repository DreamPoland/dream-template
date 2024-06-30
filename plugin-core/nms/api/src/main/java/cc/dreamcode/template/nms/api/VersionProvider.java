package cc.dreamcode.template.nms.api;

import cc.dreamcode.utilities.ClassUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.UnsafeValues;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@UtilityClass
public class VersionProvider {

    private final TreeMap<Integer, String> NEWER_NMS_VERSION = new TreeMap<>(MapBuilder.of(
            3839, "v1_20_R4"
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

        try {
            Method getDataVersion = UnsafeValues.class.getMethod("getDataVersion");
            int dataVersion = (int) getDataVersion.invoke(Bukkit.getServer().getUnsafe());
            Map.Entry<Integer, String> entry = NEWER_NMS_VERSION.floorEntry(dataVersion);

            if (entry == null) {
                throw new RuntimeException("Cannot find server version");
            }

            return entry.getValue();
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot find server version", e);
        }
    }
}
