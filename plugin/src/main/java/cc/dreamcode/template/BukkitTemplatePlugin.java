package cc.dreamcode.template;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.DocumentPersistenceComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.DocumentRepositoryComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentClassResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.nms.NmsFactory;
import cc.dreamcode.template.user.UserRepository;
import eu.hexagonmc.spigot.annotation.plugin.Plugin;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

@Plugin(
        name = "Dream-Template",
        version = "1.0-SNAPSHOT",
        spigot = @Plugin.Spigot(
                authors = "Author (dreamcode.cc)"
        )
)
public final class BukkitTemplatePlugin extends DreamBukkitPlatform {

    @Getter private static BukkitTemplatePlugin bukkitTemplatePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitTemplatePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(NmsFactory.getNmsAccessor());
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

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

        };
    }
}
