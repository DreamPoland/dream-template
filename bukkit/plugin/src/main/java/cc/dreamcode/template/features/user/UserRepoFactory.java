package cc.dreamcode.template.features.user;

import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.persistence.PersistenceHandler;
import cc.dreamcode.template.persistence.PersistenceInitializer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepoFactory implements PersistenceInitializer<UserRepoService> {

    private final PluginMain plugin;
    private final PersistenceHandler persistenceHandler;

    @Override
    public UserRepoService getRepositoryService() {
        PersistenceCollection persistenceCollection = PersistenceCollection.of(UserRepository.class);

        this.persistenceHandler.registerCollection(persistenceCollection);

        return new UserRepoService(
                this.plugin.getPluginConfig().debug,
                RepositoryDeclaration.of(UserRepository.class)
                        .newProxy(
                                this.persistenceHandler.getDatabasePersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        ),
                RepositoryDeclaration.of(UserRepository.class)
                        .newProxy(
                                this.persistenceHandler.getInMemoryPersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        )
        );
    }
}
