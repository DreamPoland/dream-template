package cc.dreamcode.template.storage.config;

import cc.dreamcode.template.storage.StorageType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.configs.annotation.Variable;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class StorageConfig extends OkaeriConfig {

    @Comment({"W jakiej formie maja byc zapisywane dane o graczu?",
            "Dostepne zapisy: (FLAT, MYSQL, MONGO, REDIS)"})
    public StorageType storageType = StorageType.FLAT;

    @Comment({"Jaki prefix ustawic dla danych?",
            "Dla FLAT prefix nie jest uzywany."})
    public String prefix = "dreamtemplate";

    @Variable("OPE_STORAGE_URI")
    @Comment("FLAT   : not applicable, plugin controlled")
    @Comment("REDIS  : redis://localhost")
    @Comment("MONGO  : mongodb://localhost:27017/db")
    @Comment("MYSQL  : jdbc:mysql://localhost:3306/db?user=root&password=1234")
    @Comment("H2     : jdbc:h2:file:./plugins/OkaeriPlatformBukkitExample/storage;mode=mysql")
    public String uri = "mongodb://localhost:27017/db";

}
