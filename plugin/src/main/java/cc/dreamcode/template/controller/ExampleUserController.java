package cc.dreamcode.template.controller;

import cc.dreamcode.template.BukkitTemplatePlugin;
import cc.dreamcode.template.user.User;
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
        final User user = this.userRepository.findOrCreateByPlayer(player).join(); // get user from repository

        user.setName(player.getName()); // example setter, update name

        this.bukkitTemplatePlugin.runAsync(user::save);// remember, all changes need to be saved (async)

        player.sendMessage("hi, " + user.getName()); // send message after changes
    }

}
