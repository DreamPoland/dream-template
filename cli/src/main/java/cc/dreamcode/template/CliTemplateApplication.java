package cc.dreamcode.template;

import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.cli.DreamCliPlatform;
import cc.dreamcode.platform.cli.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.cli.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.cli.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public final class CliTemplateApplication extends DreamCliPlatform {

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ConfigurationComponentResolver.class);
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
        // features need to be call when bot is stopping
    }

    @Override
    public DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Template", "1.0", "author");
    }

    @Override
    public OkaeriSerdesPack getPluginSerdesPack() {
        return registry -> {

        };
    }
}
