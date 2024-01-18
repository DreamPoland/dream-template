package cc.dreamcode.template.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;

import java.util.UUID;

@DocumentCollection(path = "user", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {

    default User findOrCreate(@NonNull UUID uuid, String userName) {
        User user = this.findOrCreateByPath(uuid);
        if (userName != null) {
            user.setName(userName);
        }
        return user;
    }
}
