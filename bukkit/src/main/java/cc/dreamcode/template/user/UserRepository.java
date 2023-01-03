package cc.dreamcode.template.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

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

    default User findOrCreateByHumanEntity(@NonNull HumanEntity humanEntity) {
        return this.findOrCreate(humanEntity.getUniqueId(), humanEntity.getName());
    }

    default CompletableFuture<User> findOrCreateByHumanEntityFuture(@NonNull HumanEntity humanEntity) {
        return CompletableFuture.supplyAsync(() -> this.findOrCreate(humanEntity.getUniqueId(), humanEntity.getName()));
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
