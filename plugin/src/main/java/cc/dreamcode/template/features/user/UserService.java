package cc.dreamcode.template.features.user;

import cc.dreamcode.template.persistence.PersistenceService;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.repository.DocumentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UserService extends PersistenceService<UUID, User> {
    private @Inject UserRepository userRepository;

    @Override
    public DocumentRepository<UUID, User> getDocumentRepository() {
        return this.userRepository;
    }

    public CompletableFuture<User> getOrCreate(@NonNull OfflinePlayer player) {
        final Optional<User> optionalUser = this.get(player);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            user.setName(player.getName());

            return CompletableFuture.completedFuture(user);
        }

        return CompletableFuture.supplyAsync(() -> this.getDocumentRepository().findOrCreateByPath(player.getUniqueId()));
    }

    public Optional<User> get(@NonNull OfflinePlayer player) {
        return this.getByKey(player.getUniqueId());
    }
}
