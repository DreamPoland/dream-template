package cc.dreamcode.template;

import cc.dreamcode.command.bungee.BungeeCommand;
import cc.dreamcode.notice.minecraft.bungee.serdes.BungeeNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bungee.DreamBungeeConfig;
import cc.dreamcode.platform.bungee.DreamBungeePlatform;
import cc.dreamcode.platform.bungee.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bungee.component.ListenerComponentResolver;
import cc.dreamcode.platform.bungee.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.component.resolver.CommandBindComponentResolver;
import cc.dreamcode.platform.component.resolver.CommandComponentResolver;
import cc.dreamcode.platform.component.resolver.CommandExtensionComponentResolver;
import cc.dreamcode.platform.component.resolver.CommandHandlerComponentResolver;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBungee;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.Getter;
import lombok.NonNull;

public final class BungeeTemplatePlugin extends DreamBungeePlatform implements DreamBungeeConfig, DreamPersistence {

    @Getter private static BungeeTemplatePlugin bukkitTemplatePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitTemplatePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BungeeCommand.create(this));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(CommandBindComponentResolver.class);
        componentManager.registerResolver(CommandHandlerComponentResolver.class);
        componentManager.registerResolver(CommandExtensionComponentResolver.class);

        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            componentManager.setDebug(pluginConfig.debug);

            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
        });
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Template", "1.0-InDEV", "author");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BungeeNoticeSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBungee());
        };
    }
}
