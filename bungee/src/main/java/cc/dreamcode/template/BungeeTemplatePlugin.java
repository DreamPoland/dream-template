package cc.dreamcode.template;

import cc.dreamcode.command.bungee.BungeeCommandProvider;
import cc.dreamcode.notice.bungee.BungeeNoticeProvider;
import cc.dreamcode.notice.bungee.okaeri_serdes.BungeeNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bungee.DreamBungeePlatform;
import cc.dreamcode.platform.bungee.component.CommandComponentResolver;
import cc.dreamcode.platform.bungee.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bungee.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.bungee.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.platform.bungee.component.ListenerComponentResolver;
import cc.dreamcode.platform.bungee.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.Getter;
import lombok.NonNull;

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

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig -> {
            this.getInject(BungeeCommandProvider.class).ifPresent(bungeeCommandProvider -> {
                bungeeCommandProvider.setNoPermissionMessage(messageConfig.noPermission);
                bungeeCommandProvider.setNoPlayerMessage(messageConfig.noPlayer);
            });
        });
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
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
        return DreamVersion.create("Dream-Template", "1.0", "author");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigurationSerdesPack() {
        return registry -> {
            registry.register(new BungeeNoticeSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {

        };
    }
}
