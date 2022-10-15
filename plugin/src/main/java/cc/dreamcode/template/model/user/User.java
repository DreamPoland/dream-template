package cc.dreamcode.template.model.user;

import cc.dreamcode.template.storage.document.AbstractDocument;
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
