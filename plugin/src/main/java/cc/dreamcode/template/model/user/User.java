package cc.dreamcode.template.model.user;

import cc.dreamcode.template.storage.document.AbstractDocument;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends AbstractDocument {

    private String name;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
