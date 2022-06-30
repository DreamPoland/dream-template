package cc.dreamcode.template.features.user;

import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.persistence.PersistenceHandler;
import cc.dreamcode.template.persistence.PersistenceInitializer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryFactory implements PersistenceInitializer<UserRepository> {

    private final PluginMain plugin;
    private final PersistenceHandler persistenceHandler;

    @Override
    public UserRepository getRepositoryService() {
        PersistenceCollection persistenceCollection = PersistenceCollection.of(UserRepositoryCollection.class);

        this.persistenceHandler.registerCollection(persistenceCollection);

        UserRepository userRepository = new UserRepository(
                RepositoryDeclaration.of(UserRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getDatabasePersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        ),
                RepositoryDeclaration.of(UserRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getInMemoryPersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        )
        );

        this.persistenceHandler.getRepositoryLoaderList().add(userRepository.getRepositoryLoader());

        return userRepository;
    }
}
