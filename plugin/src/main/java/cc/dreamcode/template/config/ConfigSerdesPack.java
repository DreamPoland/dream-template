package cc.dreamcode.template.config;

import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.template.notice.NoticeSerdes;
import cc.dreamcode.template.randomizer.DoubleRandomizerSerdes;
import cc.dreamcode.template.randomizer.IntRandomizerSerdes;
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
        registry.register(new NoticeSerdes());
        registry.register(new DoubleRandomizerSerdes());
        registry.register(new IntRandomizerSerdes());
        registry.register(new MenuBuilderSerdes());
    }
}
