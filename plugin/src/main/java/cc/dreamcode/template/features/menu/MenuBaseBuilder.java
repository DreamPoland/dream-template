package cc.dreamcode.template.features.menu;

import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * MenuBaseBuilder is interface for creating custom menus from config.
 */
public interface MenuBaseBuilder {

    MenuBase buildMenu(@NonNull Player player);

}
