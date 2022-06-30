package cc.dreamcode.template.persistence;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.features.user.User;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.persistence.repository.DocumentRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public interface RepositoryLoader {

    DocumentRepository getDatabaseRepository();

    DocumentRepository getCacheRepository();

    List<Field> getDocumentFields();

    default void load() {
        long start = System.currentTimeMillis();

        this.getDatabaseRepository().findAll().forEach(object -> {
            if (!(object instanceof Document)) {
                throw new PluginRuntimeException("Object is not document.");
            }

            Document document = (Document) object;
            Document documentToLoad = this.getCacheRepository().findOrCreateByPath(document.getPath());

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

        this.getDatabaseRepository().findAll().forEach(object -> {
            if (!(object instanceof Document)) {
                throw new PluginRuntimeException("Object is not document.");
            }

            Document document = (Document) object;
            if (!this.getCacheRepository().findByPath(document.getPath()).isPresent()) {
                this.getDatabaseRepository().deleteByPath(document.getPath());
            }
        });

        this.getCacheRepository().findAll().forEach(object -> {
            if (!(object instanceof Document)) {
                throw new PluginRuntimeException("Object is not document.");
            }

            Document document = (Document) object;
            Document documentToSave = this.getDatabaseRepository().findOrCreateByPath(document.getPath());

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
