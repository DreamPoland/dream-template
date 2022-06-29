package cc.dreamcode.template.features.user;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.persistence.RepositoryLoader;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepoLoader implements RepositoryLoader {

    private final boolean debug;
    private final UserRepoService userRepoService;

    @Override
    public void load() {
        long start = System.currentTimeMillis();

        this.userRepoService.getUserRepository().findAll().forEach(user -> {
            User userToLoad = this.userRepoService.getUserCacheRepository().findOrCreateByPath(user.getUniqueId());

            Arrays.stream(User.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList()).forEach(fieldName ->
                            userToLoad.set(fieldName, user.get(fieldName)));

            userToLoad.save();
        });

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie zaladowano table")
                        .name(UserRepository.class.getSimpleName())
                        .took(took)
                        .meta("size", this.userRepoService.getUserCacheRepository().count())
                        .meta("values", Arrays.stream(User.class.getDeclaredFields())
                                .map(Field::getName)
                                .collect(Collectors.joining(", ")))
                        .build()
        );
    }

    @Override
    public void save() {
        long start = System.currentTimeMillis();

        this.userRepoService.getUserCacheRepository().findAll().forEach(user -> {
            User userToSave = this.userRepoService.getUserRepository().findOrCreateByPath(user.getUniqueId());

            Arrays.stream(User.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList()).forEach(fieldName ->
                            userToSave.set(fieldName, user.get(fieldName)));

            userToSave.save();
        });

        if (this.debug) {
            long took = System.currentTimeMillis() - start;
            PluginMain.getPluginLogger().info(
                    new PluginLogger.Loader()
                            .type("Poprawnie zapisano repository")
                            .name(UserRepository.class.getSimpleName())
                            .took(took)
                            .meta("size", this.userRepoService.getUserCacheRepository().count())
                            .meta("values", Arrays.stream(User.class.getDeclaredFields())
                                    .map(Field::getName)
                                    .collect(Collectors.joining(", ")))
                            .build()
            );
        }
    }
}
