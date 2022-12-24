package cc.dreamcode.template;

import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.discord4j.DreamDiscord4JPlatform;
import cc.dreamcode.platform.discord4j.component.CommandComponentResolver;
import cc.dreamcode.platform.discord4j.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.discord4j.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.discord4j.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.platform.discord4j.component.ListenerComponentResolver;
import cc.dreamcode.platform.discord4j.component.TimerTaskComponentResolver;
import cc.dreamcode.template.command.ExampleTestCommand;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.controller.ExampleUserController;
import cc.dreamcode.template.user.TemplateUserRepository;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public final class Discord4JTemplateBot extends DreamDiscord4JPlatform {
    @Override
    public Mono<GatewayDiscordClient> load(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ConfigurationComponentResolver.class);

        final AtomicReference<Mono<GatewayDiscordClient>> atomicGateway = new AtomicReference<>();
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            // register jda
            atomicGateway.set(DiscordClient.create(pluginConfig.token).login());

            // register storage (can be registered in enable method)
            this.registerInjectable(pluginConfig.storageConfig);
            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);
        });

        return atomicGateway.get();
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(TimerTaskComponentResolver.class);

        componentManager.registerComponent(DocumentPersistence.class);
        componentManager.registerComponent(TemplateUserRepository.class);

        componentManager.registerComponent(ExampleUserController.class);
        componentManager.registerComponent(ExampleTestCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
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
