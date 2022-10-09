package cc.dreamcode.template.features.command;

import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedPlayerSetup;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedSetup;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuSetup;
import cc.dreamcode.template.TemplatePlugin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandPlatform {
    void handle(@NonNull CommandSender sender, @NonNull String[] args);

    List<String> tab(@NonNull Player player, @NonNull String[] args);

    default BukkitMenuSetup getMenuSetup(@NonNull Class<? extends BukkitMenuSetup> menuSetup) {
        return TemplatePlugin.getTemplatePlugin().createInstance(menuSetup);
    }

    default BukkitMenuPaginatedSetup getMenuPaginatedSetup(@NonNull Class<? extends BukkitMenuPaginatedSetup> menuSetup) {
        return TemplatePlugin.getTemplatePlugin().createInstance(menuSetup);
    }

    default BukkitMenuPlayerSetup getMenuPlayerSetup(@NonNull Class<? extends BukkitMenuPlayerSetup> menuSetup) {
        return TemplatePlugin.getTemplatePlugin().createInstance(menuSetup);
    }

    default BukkitMenuPaginatedPlayerSetup getMenuPaginatedPlayerSetup(@NonNull Class<? extends BukkitMenuPaginatedPlayerSetup> menuSetup) {
        return TemplatePlugin.getTemplatePlugin().createInstance(menuSetup);
    }
}
