package cc.dreamcode.template.controller;

import cc.dreamcode.platform.bungee.exception.BungeePluginException;
import cc.dreamcode.template.BungeeTemplatePlugin;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Example usage of user repository.
 * Register controller in plugin component system.
 */
public class ExampleUserController implements Listener {

    private @Inject BungeeTemplatePlugin bungeeTemplatePlugin;
    private @Inject UserRepository userRepository;

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        this.userRepository.findOrCreateByPlayer(player).whenCompleteAsync((user, throwable) -> {
            if (throwable != null) {
                throw new BungeePluginException("An error was caught when CompletableFuture is complete. (PostLoginEvent)", throwable);
            }

            user.setName(player.getName()); // example setter (only for example)
            user.save(); // save after changes (async)

            player.sendMessage("hi, " + user.getName()); // send message after save
        });
    }

}
