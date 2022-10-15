package cc.dreamcode.template.model.user;

import cc.dreamcode.template.model.FutureImpl;
import cc.dreamcode.template.model.user.User;
import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@DocumentCollection(path = "user", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User>, FutureImpl {

    default CompletableFuture<User> findOrCreate(@NonNull UUID uuid, String userName) {
        return future(() -> {
            User user = this.findOrCreateByPath(uuid);
            if (userName != null) {
                user.setName(userName);
            }
            return user;
        });
    }

    default CompletableFuture<User> findOrCreateByUUID(@NonNull UUID uuid) {
        return this.findOrCreate(uuid, null);
    }

    default CompletableFuture<User> findOrCreateByPlayer(@NonNull Player player) {
        return this.findOrCreate(player.getUniqueId(), player.getName());
    }

    default CompletableFuture<Optional<User>> findByName(@NonNull String name, boolean ignoreCase) {
        return future(() -> this.streamAll()
                .filter(user -> ignoreCase
                        ? user.getName().equalsIgnoreCase(name)
                        : user.getName().equals(name))
                .findFirst());
    }

}
