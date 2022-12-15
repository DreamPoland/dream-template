package cc.dreamcode.template.mcversion;

import cc.dreamcode.template.mcversion.api.VersionAccessor;
import cc.dreamcode.template.mcversion.v1_12_R1.V1_12_R1_VersionAccessor;
import cc.dreamcode.template.mcversion.v1_16_R3.V1_16_R3_VersionAccessor;
import cc.dreamcode.template.mcversion.v1_17_R1.V1_17_R1_VersionAccessor;
import cc.dreamcode.template.mcversion.v1_18_R2.V1_18_R2_VersionAccessor;
import cc.dreamcode.template.mcversion.v1_19_R2.V1_19_R2_VersionAccessor;
import cc.dreamcode.template.mcversion.v1_8_R3.V1_8_R3_VersionAccessor;
import com.cryptomorin.xseries.ReflectionUtils;

public class VersionProvider {
    public static VersionAccessor getVersionAccessor() {
        switch (ReflectionUtils.VERSION) {
            case "v1_8_R3": {
                return new V1_8_R3_VersionAccessor();
            }
            case "v1_12_R1": {
                return new V1_12_R1_VersionAccessor();
            }
            case "v1_16_R3": {
                return new V1_16_R3_VersionAccessor();
            }
            case "v1_17_R1": {
                return new V1_17_R1_VersionAccessor();
            }
            case "v1_18_R2": {
                return new V1_18_R2_VersionAccessor();
            }
            case "v1_19_R2": {
                return new V1_19_R2_VersionAccessor();
            }
            default: {
                throw new RuntimeException("Plugin doesn't support this server version, update to: 1.8.8, 1.12.2, 1.16.5, 1.17.1, 1.18.2 or 1.19.3.");
            }
        }
    }
}
