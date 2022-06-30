package cc.dreamcode.template.persistence;

import cc.dreamcode.template.config.subconfig.StorageConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class PersistenceFactory {

    private final StorageConfig storageConfig;

    public DocumentPersistence getDatabasePersistence(File pluginFolder) {
        final PersistencePath persistencePath = PersistencePath.of(this.storageConfig.prefix);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch (ClassNotFoundException ignored) { }

        try {
            Class.forName("org.h2.Driver");
        }
        catch (ClassNotFoundException ignored) { }

        switch (this.storageConfig.backendSave) {
            case FLAT:
                return new DocumentPersistence(
                        new FlatPersistence(
                                pluginFolder,
                                ".yml"
                        ),
                        YamlBukkitConfigurer::new,
                        new SerdesBukkit(),
                        new PersistenceSerdesPack()
                );
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(this.storageConfig.uri);

                return new DocumentPersistence(
                        new MariaDbPersistence(
                                persistencePath,
                                mariadbHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new PersistenceSerdesPack()
                );
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(this.storageConfig.uri);

                return new DocumentPersistence(
                        new H2Persistence(
                                persistencePath,
                                jdbcHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new PersistenceSerdesPack()
                );
            case REDIS:
                RedisURI redisURI = RedisURI.create(this.storageConfig.uri);
                RedisClient redisClient = RedisClient.create(redisURI);

                return new DocumentPersistence(
                        new RedisPersistence(
                                persistencePath,
                                redisClient
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new PersistenceSerdesPack()
                );
            case MONGO:
                MongoClientURI mongoUri = new MongoClientURI(this.storageConfig.uri);
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
                        new PersistenceSerdesPack()
                );
            default:
                throw new RuntimeException("Unknown data type: " + this.storageConfig.backendSave);
        }
    }


}
