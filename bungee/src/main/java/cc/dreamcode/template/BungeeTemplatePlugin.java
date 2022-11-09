package cc.dreamcode.template;

import cc.dreamcode.command.bungee.BungeeCommandProvider;
import cc.dreamcode.notice.bungee.BungeeNoticeProvider;
import cc.dreamcode.notice.bungee.okaeri_serdes.BungeeNoticeSerdes;
import cc.dreamcode.platform.bungee.DreamBungeePlatform;
import cc.dreamcode.platform.bungee.component.CommandComponentClassResolver;
import cc.dreamcode.platform.bungee.component.ConfigurationComponentClassResolver;
import cc.dreamcode.platform.bungee.component.DocumentPersistenceComponentClassResolver;
import cc.dreamcode.platform.bungee.component.DocumentRepositoryComponentClassResolver;
import cc.dreamcode.platform.bungee.component.ListenerComponentClassResolver;
import cc.dreamcode.platform.bungee.component.RunnableComponentClassResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.user.UserRepository;
import eu.hexagonmc.spigot.annotation.plugin.Plugin;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.Getter;
import lombok.NonNull;

@Plugin(
        name = "Dream-Template",
        version = "1.0-SNAPSHOT",
        bungee = @Plugin.Bungee(
                author = "Author (dreamcode.cc)"
        )
)
public final class BungeeTemplatePlugin extends DreamBungeePlatform {

    @Getter private static BungeeTemplatePlugin bukkitTemplatePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitTemplatePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BungeeNoticeProvider.create(this));
        this.registerInjectable(BungeeCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentClassResolver.class);
        componentManager.registerResolver(ListenerComponentClassResolver.class);
        componentManager.registerResolver(RunnableComponentClassResolver.class);

        componentManager.registerResolver(ConfigurationComponentClassResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentClassResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentClassResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
        });
    }

    @Override
    public void disable() {
        // features need to be call by stop server
    }

    @Override
    public OkaeriSerdesPack getPluginSerdesPack() {
        return registry -> {
            registry.register(new BungeeNoticeSerdes());
        };
    }
}
