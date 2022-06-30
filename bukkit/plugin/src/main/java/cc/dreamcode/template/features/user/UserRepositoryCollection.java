package cc.dreamcode.template.features.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@DocumentCollection(path = "users", keyLength = 36)
public interface UserRepositoryCollection extends DocumentRepository<UUID, User> {

    default User getOrCreate(OfflinePlayer player) {
        User user = this.findOrCreateByPath(player.getUniqueId());
        if (player.getName() != null) {
            user.setName(player.getName());
        }

        return user;
    }

}
