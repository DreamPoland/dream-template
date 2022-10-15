package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.storage.StorageSerdesPack;
import cc.dreamcode.template.storage.config.StorageConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class DocumentPersistenceComponentClassResolver extends ComponentClassResolver<Class<DocumentPersistence>> {

    private @Inject TemplatePlugin templatePlugin;
    private @Inject PluginConfig pluginConfig;

    @Override
    public boolean isAssignableFrom(@NonNull Class<DocumentPersistence> documentPersistenceClass) {
        return DocumentPersistence.class.isAssignableFrom(documentPersistenceClass);
    }

    @Override
    public String getComponentName() {
        return "persistence";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<DocumentPersistence> documentPersistenceClass) {
        return new HashMap<>();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<DocumentPersistence> documentPersistenceClass) {
        final StorageConfig storageConfig = this.pluginConfig.storageConfig;
        final PersistencePath persistencePath = PersistencePath.of(storageConfig.prefix);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch (ClassNotFoundException ignored) { }

        switch (storageConfig.storageType) {
            case FLAT:
                return new DocumentPersistence(
                        new FlatPersistence(
                                this.templatePlugin.getDataFolder(),
                                ".yml"
                        ),
                        YamlBukkitConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(
                        new MariaDbPersistence(
                                persistencePath,
                                mariadbHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(
                        new H2Persistence(
                                persistencePath,
                                jdbcHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case REDIS:
                RedisURI redisURI = RedisURI.create(storageConfig.uri);
                RedisClient redisClient = RedisClient.create(redisURI);

                return new DocumentPersistence(
                        new RedisPersistence(
                                persistencePath,
                                redisClient
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case MONGODB:
                MongoClientURI mongoUri = new MongoClientURI(storageConfig.uri);
                MongoClient mongoClient = new MongoClient(mongoUri);

                if (mongoUri.getDatabase() == null) {
                    throw new IllegalArgumentException("Mongo URI database not found: " + mongoUri.getURI());
                }

                return new DocumentPersistence(
                        new MongoPersistence(
                                persistencePath,
                                mongoClient,
                                mongoUri.getDatabase()
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            default:
                throw new RuntimeException("Unknown data type: " + storageConfig.storageType);
        }
    }
}
