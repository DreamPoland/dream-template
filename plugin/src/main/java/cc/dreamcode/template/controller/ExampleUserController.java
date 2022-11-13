package cc.dreamcode.template.controller;

import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.template.BukkitTemplatePlugin;
import cc.dreamcode.template.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Example usage of user repository.
 * Register controller in plugin component system.
 */
public class ExampleUserController implements Listener {

    private @Inject BukkitTemplatePlugin bukkitTemplatePlugin;
    private @Inject UserRepository userRepository;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        this.userRepository.findOrCreateByPlayer(player).whenCompleteAsync((user, throwable) -> {
            if (throwable != null) {
                throw new BukkitPluginException("An error was caught when CompletableFuture is complete. (PlayerJoinEvent)", throwable);
            }

            user.setName(player.getName()); // example setter (only for example)
            user.save(); // save after changes (async)

            player.sendMessage("hi, " + user.getName()); // send message after save
        });
    }

}
