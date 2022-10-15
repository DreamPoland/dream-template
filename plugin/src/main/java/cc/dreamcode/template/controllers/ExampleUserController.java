package cc.dreamcode.template.controllers;

import cc.dreamcode.template.model.user.User;
import cc.dreamcode.template.model.user.UserRepository;
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

    private @Inject UserRepository userRepository;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final User user = this.userRepository.findOrCreateByPlayer(player).join(); // get user from repository

        user.setName(player.getName()); // example setter, update name

        user.saveAsync(); // remember, all changes need to be saved (async or no)

        player.sendMessage("hi, " + user.getName()); // send message after changes
    }

}
