package cc.dreamcode.template.features.user;

import cc.dreamcode.template.TemplatePlugin;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserController implements Listener {

    private @Inject TemplatePlugin templatePlugin;
    private @Inject UserService userService;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        // load data from database to InMemory data
        this.templatePlugin.getServer().getScheduler().runTaskAsynchronously(this.templatePlugin, () -> {
            this.userService.load(e.getPlayer().getUniqueId());
            this.userService.getOrCreate(e.getPlayer()).save();
        });
    }
}
