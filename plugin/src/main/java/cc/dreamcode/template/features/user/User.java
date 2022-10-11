package cc.dreamcode.template.features.user;

import cc.dreamcode.template.persistence.document.AbstractDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractDocument {

    private String name;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
