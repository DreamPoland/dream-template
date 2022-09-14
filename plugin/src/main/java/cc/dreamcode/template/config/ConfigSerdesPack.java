package cc.dreamcode.template.config;

import cc.dreamcode.template.features.menu.MenuSerdes;
import cc.dreamcode.template.features.notice.NoticeSerdes;
import cc.dreamcode.template.features.randomizer.DoubleRandomizerSerdes;
import cc.dreamcode.template.features.randomizer.IntRandomizerSerdes;
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
        registry.register(new MenuSerdes());
        registry.register(new DoubleRandomizerSerdes());
        registry.register(new IntRandomizerSerdes());
    }
}
