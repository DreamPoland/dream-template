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

    default CompletableFuture<User> findOrCreate(@NonNull UUID uuid, String userName) {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            User user = this.findOrCreateByPath(uuid);
            if (userName != null) {
                user.setName(userName);
            }
            completableFuture.complete(user);
        });

        return completableFuture;
    }

    default CompletableFuture<User> findOrCreateByUUID(@NonNull UUID uuid) {
        return this.findOrCreate(uuid, null);
    }

    default CompletableFuture<User> findOrCreateByPlayer(@NonNull ProxiedPlayer player) {
        return this.findOrCreate(player.getUniqueId(), player.getName());
    }

    default CompletableFuture<Optional<User>> findByName(@NonNull String name, boolean ignoreCase) {
        CompletableFuture<Optional<User>> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            Optional<User> optionalUser = this.streamAll()
                    .filter(user -> ignoreCase
                            ? user.getName().equalsIgnoreCase(name)
                            : user.getName().equals(name))
                    .findFirst();

            completableFuture.complete(optionalUser);
        });

        return completableFuture;
    }

}
