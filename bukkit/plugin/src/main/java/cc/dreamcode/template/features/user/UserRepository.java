package cc.dreamcode.template.features.user;

import cc.dreamcode.template.persistence.Repository;
import cc.dreamcode.template.persistence.RepositoryLoader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepository implements Repository<UserRepositoryCollection, OfflinePlayer, User> {

    @Getter private final UserRepositoryCollection userDatabaseRepository;
    @Getter private final UserRepositoryCollection userCacheRepository;

    @Override
    public RepositoryLoader<UserRepositoryCollection> getRepositoryLoader() {
        return new RepositoryLoader<UserRepositoryCollection>() {
            @Override
            public UserRepositoryCollection getDatabaseRepository() {
                return userDatabaseRepository;
            }

            @Override
            public UserRepositoryCollection getCacheRepository() {
                return userCacheRepository;
            }

            @Override
            public List<Field> getDocumentFields() {
                return Arrays.stream(User.class.getDeclaredFields()).collect(Collectors.toList());
            }
        };
    }

    @Override
    public boolean contains(OfflinePlayer offlinePlayer) {
        return this.userCacheRepository.existsByPath(offlinePlayer.getUniqueId());
    }

    @Override
    public Optional<User> getByKey(OfflinePlayer offlinePlayer) {
        return this.userCacheRepository.findByPath(offlinePlayer.getUniqueId());
    }

    @Override
    public Set<User> getSet() {
        return new HashSet<>(this.userCacheRepository.findAll());
    }

    @Override
    public User getOrCreate(OfflinePlayer offlinePlayer) {
        return this.userCacheRepository.getOrCreate(offlinePlayer);
    }

    @Override
    public void remove(OfflinePlayer offlinePlayer) {
        this.userCacheRepository.deleteByPath(offlinePlayer.getUniqueId());
    }
}
