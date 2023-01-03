package cc.dreamcode.template.controller;

import cc.dreamcode.template.BungeeTemplatePlugin;
import cc.dreamcode.template.user.User;
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
        final User user = this.userRepository.findOrCreateByPlayerFuture(player).join(); // async get user

        user.setName(player.getName()); // example setter (only for example)
        this.bungeeTemplatePlugin.runAsync(user::save); // save after changes (async)

        player.sendMessage("hi, " + user.getName()); // send message after save
    }

}
