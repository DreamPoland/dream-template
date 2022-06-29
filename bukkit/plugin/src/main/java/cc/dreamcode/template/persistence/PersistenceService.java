package cc.dreamcode.template.persistence;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.config.subconfig.StorageConfig;
import cc.dreamcode.template.exception.PluginRuntimeException;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.InMemoryDocumentPersistence;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class PersistenceService {

    private final PluginMain pluginMain;
    private final PersistenceHandler persistenceHandler;

    public void initializePersistence() {
        final StorageConfig storageConfig = this.pluginMain.getPluginConfig().storageConfig;
        long start = System.currentTimeMillis();

        this.persistenceHandler.setInMemoryPersistence(new InMemoryDocumentPersistence(
                new SerdesBukkit(),
                new PersistenceSerdesPack()
        ));

        this.persistenceHandler.setDatabasePersistence(new PersistenceFactory(storageConfig)
                .getDatabasePersistence(this.pluginMain.getDataFolder()));

        if (storageConfig.saveDataTimer <= 20) {
            throw new PluginRuntimeException("SaveDataTimer can not be lower than 20.");
        }

        this.pluginMain.getServer().getScheduler().runTaskTimerAsynchronously(
                this.pluginMain,
                () -> this.pluginMain.getPersistenceService().savePersistence(false, this.pluginMain.repositoryLoaders()),
                storageConfig.saveDataTimer,
                storageConfig.saveDataTimer
        );

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Podlaczono database")
                        .name(this.persistenceHandler.getDatabasePersistence().getBasePath().getValue())
                        .meta("backend-save", storageConfig.backendSave)
                        .meta("prefix", storageConfig.prefix)
                        .took(took)
                        .build()
        );
    }

    public void savePersistence(boolean close, Collection<RepositoryLoader> repositoryLoaders) {
        final StorageConfig storageConfig = this.pluginMain.getPluginConfig().storageConfig;

        try {
            long start = System.currentTimeMillis();

            repositoryLoaders.forEach(RepositoryLoader::save);

            if (close) {
                this.persistenceHandler.getDatabasePersistence().close();
                this.persistenceHandler.getInMemoryPersistence().close();

                long took = System.currentTimeMillis() - start;
                PluginMain.getPluginLogger().info(
                        new PluginLogger.Loader()
                                .type("Zamknieto polaczenie")
                                .name(this.persistenceHandler.getDatabasePersistence().getBasePath().getValue())
                                .meta("backend-save", storageConfig.backendSave)
                                .meta("prefix", storageConfig.prefix)
                                .took(took)
                                .build()
                );
            }
        } catch (IOException e) {
            PluginMain.getPluginLogger().error("Cannot interact with database.", e);
        }
    }


}
