package cc.dreamcode.template;

import cc.dreamcode.template.commands.admin.PluginCMD;
import cc.dreamcode.template.component.ComponentHandler;
import cc.dreamcode.template.config.ConfigLoader;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.features.menu.MenuActionHandler;
import cc.dreamcode.template.features.user.UserRepositoryFactory;
import cc.dreamcode.template.features.user.UserRepository;
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
import cc.dreamcode.template.persistence.PersistenceHandler;
import cc.dreamcode.template.persistence.PersistenceService;
import cc.dreamcode.template.persistence.RepositoryLoader;
import com.cryptomorin.xseries.ReflectionUtils;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

@Plugin(name = "Dream-Template", version = "1.0-SNAPSHOT")
@Author("Ravis96")
@Description("Template plugin for DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
public final class PluginMain extends PluginBootLoader {

    @Getter private static PluginMain pluginMain;
    @Getter private static PluginLogger pluginLogger;

    @Getter private PluginConfig pluginConfig;
    @Getter private MessageConfig messageConfig;

    @Getter private PersistenceService persistenceService;

    @Getter private UserRepository userRepository;

    @Getter @Setter Injector injector;
    @Getter private NmsAccessor nmsAccessor;
    @Getter private ComponentHandler componentHandler;

    @Override
    public void load() {
        pluginMain = this;
        pluginLogger = new PluginLogger(pluginMain.getLogger());

        this.injector = OkaeriInjector.create();
        this.nmsAccessor = this.hookNmsAccessor();

        try {
            this.messageConfig = new ConfigLoader(new File(this.getDataFolder(), "message.yml")).loadMessageConfig();
            this.pluginConfig = new ConfigLoader(new File(this.getDataFolder(), "config.yml")).loadPluginConfig();
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when config files are loading...", e, this);
        }
    }

    @Override
    public void start() {
        if (!PluginFactory.checkPlugin(this.getPluginDisabled(), this.getDescription())) {
            return;
        }

        try {
            // connect database
            this.persistenceService = new PersistenceService(
                    this,
                    new PersistenceHandler()
            );
            this.persistenceService.initializePersistence();

            // Add database tables
            this.userRepository = new UserRepositoryFactory(
                    this,
                    this.persistenceService.getPersistenceHandler()
            ).getRepositoryService();

            // load database to cache
            this.repositoryLoaders().forEach(RepositoryLoader::load);

            // register other services

        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when services are loading...", e, this);
        }

        this.injector.registerInjectable(this)
                .registerInjectable(this.nmsAccessor)
                .registerInjectable(this.pluginConfig)
                .registerInjectable(this.messageConfig)

                .registerInjectable(this.userRepository);

        // register components (commands, listener, task or else (need implement))
        this.componentHandler = new ComponentHandler(this)
                .registerComponent(new PluginCMD())
                .registerComponent(new MenuActionHandler());

        PluginMain.getPluginLogger().info(String.format("Aktywna wersja: v%s - Autor: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    @Override
    public void stop() {
        // save cache to database
        this.persistenceService.savePersistence(true, this.repositoryLoaders());

        PluginMain.getPluginLogger().info(String.format("Aktywna wersja: v%s - Autor: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    /**
     * Complete all repository loader by writing it below.
     */
    public Collection<RepositoryLoader> repositoryLoaders() {
        return Collections.singletonList(
                this.userRepository.getRepositoryLoader()
        );
    }

    public NmsAccessor hookNmsAccessor() {
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Podlaczam wersje minecraft")
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
