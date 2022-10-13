package cc.dreamcode.template.features.user;

import cc.dreamcode.template.persistence.PersistenceService;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.repository.DocumentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService extends PersistenceService<UUID, User> {
    private @Inject UserRepository userRepository;

    @Override
    public DocumentRepository<UUID, User> getDocumentRepository() {
        return this.userRepository;
    }

    public Optional<User> get(@NonNull OfflinePlayer player) {
        final Optional<User> userOptional = this.getByKey(player.getUniqueId());
        userOptional.ifPresent(user -> user.setName(player.getName()));
        return userOptional;
    }

    public User getOrCreate(@NonNull OfflinePlayer player) {
        final Optional<User> optionalUser = this.get(player);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            user.setName(player.getName());

            return user;
        }

        final User user = this.getDocumentRepository().findOrCreateByPath(player.getUniqueId());
        user.setName(player.getName());

        return user;
    }

    public Optional<User> getByName(@NonNull String name) {
        return this.getSet()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst();
    }

}
