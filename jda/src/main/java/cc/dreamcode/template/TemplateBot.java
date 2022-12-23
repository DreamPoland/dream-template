package cc.dreamcode.template;

import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.cli.DreamCliPlatform;
import cc.dreamcode.platform.cli.component.ConfigurationComponentClassResolver;
import cc.dreamcode.platform.cli.component.DocumentPersistenceComponentClassResolver;
import cc.dreamcode.platform.cli.component.DocumentRepositoryComponentClassResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

@NoArgsConstructor
public final class TemplateBot extends DreamCliPlatform {

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ConfigurationComponentClassResolver.class);
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            // JDABuilder from JDA example
            JDABuilder builder = JDABuilder.createDefault(pluginConfig.token);

            // Disable parts of the cache
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            // Enable the bulk delete event
            builder.setBulkDeleteSplittingEnabled(false);
            // Set activity (like "playing Something")
            builder.setActivity(Activity.watching("TV"));

            // register jda
            this.registerInjectable(builder.build());

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
