package cc.dreamcode.template.config;

import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.okaeri_serdes.BukkitNoticeSerdes;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;

/**
 * Serialize/deserialize classes for objects in config.
 * See: ObjectSerializer.class
 */
public class ConfigSerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new BukkitNoticeSerdes());
        registry.register(new MenuBuilderSerdes());
    }
}
