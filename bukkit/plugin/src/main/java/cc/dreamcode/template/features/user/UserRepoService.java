package cc.dreamcode.template.features.user;

import cc.dreamcode.template.persistence.Repository;
import cc.dreamcode.template.persistence.RepositoryLoader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserRepoService implements Repository<OfflinePlayer, User> {

    private final boolean debug;

    @Getter private final UserRepository userRepository;
    @Getter private final UserRepository userCacheRepository;

    @Override
    public RepositoryLoader getRepositoryLoader() {
        return new UserRepoLoader(this.debug, this);
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
