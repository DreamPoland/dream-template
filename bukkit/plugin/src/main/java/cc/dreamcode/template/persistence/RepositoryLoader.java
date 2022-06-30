package cc.dreamcode.template.persistence;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.features.user.User;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.persistence.repository.DocumentRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public interface RepositoryLoader<PATH, T extends Document> {

    DocumentRepository<PATH, T> getDatabaseRepository();

    DocumentRepository<PATH, T> getCacheRepository();

    List<Field> getDocumentFields();

    default void load() {
        long start = System.currentTimeMillis();

        this.getDatabaseRepository().findAll().forEach(document -> {
            Document documentToLoad = this.getCacheRepository().findOrCreateByPath((PATH) document.getPath());

            this.getDocumentFields().stream()
                    .map(Field::getName)
                    .forEach(fieldName -> {
                        documentToLoad.set(fieldName, document.get(fieldName));
                    });

            documentToLoad.save();
        });

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie zaladowano repository")
                        .name(this.getCacheRepository().getCollection().getValue())
                        .took(took)
                        .meta("size", this.getCacheRepository().count())
                        .meta("values", Arrays.stream(User.class.getDeclaredFields())
                                .map(Field::getName)
                                .collect(Collectors.joining(", ")))
                        .build()
        );
    }

    default void save(boolean printNotice) {
        long start = System.currentTimeMillis();

        this.getDatabaseRepository().findAll().forEach(document -> {
            if (!this.getCacheRepository().findByPath((PATH) document.getPath()).isPresent()) {
                this.getDatabaseRepository().deleteByPath((PATH) document.getPath());
            }
        });

        this.getCacheRepository().findAll().forEach(document -> {
            Document documentToSave = this.getDatabaseRepository().findOrCreateByPath((PATH) document.getPath());

            this.getDocumentFields().stream()
                    .map(Field::getName)
                    .forEach(fieldName -> {
                        documentToSave.set(fieldName, document.get(fieldName));
                    });

            documentToSave.save();
        });

        if (printNotice) {
            long took = System.currentTimeMillis() - start;
            PluginMain.getPluginLogger().info(
                    new PluginLogger.Loader()
                            .type("Poprawnie zapisano repository")
                            .name(this.getCacheRepository().getCollection().getValue())
                            .took(took)
                            .meta("size", this.getDatabaseRepository().count())
                            .meta("values", Arrays.stream(User.class.getDeclaredFields())
                                    .map(Field::getName)
                                    .collect(Collectors.joining(", ")))
                            .build()
            );
        }
    }

}
