package cc.dreamcode.template;

import cc.dreamcode.template.component.ComponentHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.features.hook.HookFactory;
import cc.dreamcode.template.features.hook.HookManager;
import cc.dreamcode.template.features.hook.plugins.FunnyGuildsHook;
import cc.dreamcode.template.features.menu.MenuActionHandler;
import cc.dreamcode.template.features.user.UserActionHandler;
import cc.dreamcode.template.features.user.UserRepository;
import cc.dreamcode.template.features.user.UserService;
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
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Plugin(name = "Dream-Template", version = "1.0-SNAPSHOT")
@Author("Ravis96")
@Description("Template plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

@SoftDependency("FunnyGuilds")
public final class PluginMain extends PluginBootLoader {

    @Getter private static PluginMain pluginMain;
    @Getter private static PluginLogger pluginLogger;

    @Getter private Injector injector;
    @Getter private Tasker tasker;
    @Getter private NmsAccessor nmsAccessor;

    @Override
    public void load() {
        pluginMain = this;
        pluginLogger = new PluginLogger(pluginMain.getLogger());

        this.injector = OkaeriInjector.create();
        this.injector.registerInjectable(this);

        this.tasker = BukkitTasker.newPool(this);
        this.injector.registerInjectable(this.tasker);

        this.nmsAccessor = this.hookNmsAccessor();
        this.injector.registerInjectable(this.nmsAccessor);
    }

    @Override
    public void start() {
        if (!PluginFactory.checkPlugin(this.getPluginDisabled(), this.getDescription())) {
            return;
        }

        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin.
        // Remember to sort it by type of class:
        // --> Config, DocumentPersistence, DocumentRepository, Services, Managers, Commands, Listeners, Runnables, other...
        new ComponentHandler(this.getInjector())
                .registerComponent(PluginConfig.class)
                .registerComponent(MessageConfig.class)
                .registerComponent(DocumentPersistence.class)
                .registerComponent(UserRepository.class)
                .registerComponent(UserService.class)
                .registerComponent(UserActionHandler.class)
                .registerComponent(MenuActionHandler.class)
                .registerComponent(HookManager.class, hookManager ->
                        this.createInstance(HookFactory.class).tryLoadAllDepends(Stream.of(
                                FunnyGuildsHook.class
                        ).collect(Collectors.toList()), hookManager));

        PluginMain.getPluginLogger().info(String.format("Active version: v%s - Author: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    @Override
    public void stop() {
        // features need to be call by stop server

        PluginMain.getPluginLogger().info(String.format("Active version: v%s - Author: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    public NmsAccessor hookNmsAccessor() {
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
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

    public <T> T createInstance(@NonNull Class<T> type) {
        return this.injector.createInstance(type);
    }
}
