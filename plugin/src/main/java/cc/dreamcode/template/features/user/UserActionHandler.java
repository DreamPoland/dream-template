package cc.dreamcode.template.features.user;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserActionHandler implements Listener {

    private @Inject Tasker tasker;
    private @Inject UserRepository userRepository;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        this.tasker.newChain()
                .async(() -> this.userRepository.get(player, true))
                .acceptAsync(user -> {
                    user.save();
                    this.userRepository.getRepositoryLoader().save(user);
                })
                .execute();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        this.tasker.newChain()
                .async(() -> this.userRepository.get(player, true))
                .acceptAsync(user -> {
                    user.save();
                    this.userRepository.getRepositoryLoader().save(user);
                })
                .execute();
    }
}
