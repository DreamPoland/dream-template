package cc.dreamcode.template;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.okaeri.MenuBuilderSerializer;
import cc.dreamcode.notice.minecraft.adventure.bukkit.AdventureBukkitNoticeProvider;
import cc.dreamcode.notice.minecraft.serdes.AdventureBukkitNoticeSerializer;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.ConfigurationResolver;
import cc.dreamcode.platform.bukkit.serializer.ItemMetaSerializer;
import cc.dreamcode.platform.bukkit.serializer.ItemStackSerializer;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.other.component.DreamCommandExtension;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryResolver;
import cc.dreamcode.template.command.ExampleCommand;
import cc.dreamcode.template.command.handler.InvalidInputHandlerImpl;
import cc.dreamcode.template.command.handler.InvalidPermissionHandlerImpl;
import cc.dreamcode.template.command.handler.InvalidSenderHandlerImpl;
import cc.dreamcode.template.command.handler.InvalidUsageHandlerImpl;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.mcversion.VersionProvider;
import cc.dreamcode.template.profile.ProfileRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class TemplatePlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static TemplatePlugin templatePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        templatePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.setDebug(false);

        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this));
        this.registerInjectable(AdventureBukkitNoticeProvider.create(this));

        this.registerInjectable(VersionProvider.getVersionAccessor());
        componentManager.registerExtension(DreamCommandExtension.class);

        componentManager.registerResolver(ConfigurationResolver.class);
        componentManager.registerComponent(MessageConfig.class);

        componentManager.registerComponent(InvalidInputHandlerImpl.class);
        componentManager.registerComponent(InvalidPermissionHandlerImpl.class);
        componentManager.registerComponent(InvalidSenderHandlerImpl.class);
        componentManager.registerComponent(InvalidUsageHandlerImpl.class);

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            componentManager.setDebug(pluginConfig.debug);

            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceResolver.class);
            componentManager.registerComponent(DocumentPersistence.class);

            componentManager.registerResolver(DocumentRepositoryResolver.class);
            componentManager.registerComponent(ProfileRepository.class);
        });

        componentManager.registerComponent(ExampleCommand.class);
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
            registry.register(new AdventureBukkitNoticeSerializer());
            registry.register(new MenuBuilderSerializer());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBukkit());

            registry.registerExclusive(ItemStack.class, new ItemStackSerializer());
            registry.registerExclusive(ItemMeta.class, new ItemMetaSerializer());
        };
    }

}
