package cc.dreamcode.template.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@DocumentCollection(path = "user", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {

    default User findOrCreate(@NonNull UUID uuid, String userName) {
        User user = this.findOrCreateByPath(uuid);
        if (userName != null) {
            user.setName(userName);
        }
        return user;
    }

    default CompletableFuture<User> findOrCreateFuture(@NonNull UUID uuid, String userName) {
        return CompletableFuture.supplyAsync(() -> this.findOrCreate(uuid, userName));
    }

    default User findOrCreateByUUID(@NonNull UUID uuid) {
        return this.findOrCreate(uuid, null);
    }

    default CompletableFuture<User> findOrCreateByUUIDFuture(@NonNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> this.findOrCreate(uuid, null));
    }

    default User findOrCreateByPlayer(@NonNull ProxiedPlayer player) {
        return this.findOrCreate(player.getUniqueId(), player.getName());
    }

    default CompletableFuture<User> findOrCreateByPlayerFuture(@NonNull ProxiedPlayer player) {
        return CompletableFuture.supplyAsync(() -> this.findOrCreate(player.getUniqueId(), player.getName()));
    }

    default Optional<User> findByName(@NonNull String name, boolean ignoreCase) {
        return this.streamAll()
                .filter(user -> ignoreCase
                        ? user.getName().equalsIgnoreCase(name)
                        : user.getName().equals(name))
                .findFirst();
    }

    default CompletableFuture<Optional<User>> findByNameFuture(@NonNull String name, boolean ignoreCase) {
        return CompletableFuture.supplyAsync(() -> this.streamAll()
                .filter(user -> ignoreCase
                        ? user.getName().equalsIgnoreCase(name)
                        : user.getName().equals(name))
                .findFirst());
    }

}
