package cc.dreamcode.template.profile;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;

import java.util.UUID;

@DocumentCollection(path = "profiles", keyLength = 36)
public interface ProfileRepository extends DocumentRepository<UUID, Profile> {

    default Profile findOrCreate(@NonNull UUID uuid, String profileName) {

        Profile profile = this.findOrCreateByPath(uuid);
        if (profileName != null) {
            profile.setName(profileName);
        }

        return profile;
    }
}
