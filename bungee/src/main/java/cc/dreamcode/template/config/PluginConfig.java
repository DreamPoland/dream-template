package cc.dreamcode.template.config;

import cc.dreamcode.platform.bungee.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Template (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamtemplate");
}
