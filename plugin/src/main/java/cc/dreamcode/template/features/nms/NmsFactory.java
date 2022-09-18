package cc.dreamcode.template.features.nms;

import cc.dreamcode.template.TemplateLogger;
import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.nms.api.NmsAccessor;
import cc.dreamcode.template.nms.v1_10_R1.V1_10_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_11_R1.V1_11_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_12_R1.V1_12_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_13_R2.V1_13_R2_NmsAccessor;
import cc.dreamcode.template.nms.v1_14_R1.V1_14_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_15_R1.V1_15_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_16_R3.V1_16_R3_NmsAccessor;
import cc.dreamcode.template.nms.v1_17_R1.V1_17_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_18_R2.V1_18_R2_NmsAccessor;
import cc.dreamcode.template.nms.v1_19_R1.V1_19_R1_NmsAccessor;
import cc.dreamcode.template.nms.v1_8_R3.V1_8_R3_NmsAccessor;
import cc.dreamcode.template.nms.v1_9_R2.V1_9_R2_NmsAccessor;
import com.cryptomorin.xseries.ReflectionUtils;

/**
 * NmsAccessor collect all version into one api, witch you can easily send packets to minecraft clients.
 * When you don't have to use it, just remove class & nms implements from gradle dependencies.
 */
public final class NmsFactory {
    public static NmsAccessor getNmsAccessor() {
        TemplatePlugin.getTemplateLogger().info(
                new TemplateLogger.Loader()
                        .type("Connect with minecraft version")
                        .name(ReflectionUtils.VERSION)
                        .build()
        );

        switch (ReflectionUtils.VER) {
            case 8: {
                return new V1_8_R3_NmsAccessor();
            }
            case 9: {
                return new V1_9_R2_NmsAccessor();
            }
            case 10: {
                return new V1_10_R1_NmsAccessor();
            }
            case 11: {
                return new V1_11_R1_NmsAccessor();
            }
            case 12: {
                return new V1_12_R1_NmsAccessor();
            }
            case 13: {
                return new V1_13_R2_NmsAccessor();
            }
            case 14: {
                return new V1_14_R1_NmsAccessor();
            }
            case 15: {
                return new V1_15_R1_NmsAccessor();
            }
            case 16: {
                return new V1_16_R3_NmsAccessor();
            }
            case 17: {
                return new V1_17_R1_NmsAccessor();
            }
            case 18: {
                return new V1_18_R2_NmsAccessor();
            }
            case 19: {
                return new V1_19_R1_NmsAccessor();
            }
            default: {
                throw new PluginRuntimeException("Plugin doesn't support this server version, change to 1.8 - 1.19 (latest subversion).");
            }
        }
    }
}
