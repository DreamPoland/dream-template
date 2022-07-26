package cc.dreamcode.template.features.user;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    private String name;

    private String value = "default values -- add data";

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.getUniqueId()));
    }

}
