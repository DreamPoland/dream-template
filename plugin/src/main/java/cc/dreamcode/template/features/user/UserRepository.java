package cc.dreamcode.template.features.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.util.UUID;

@DocumentCollection(path = "users", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {
    // Repository control moved to UserService.
}
