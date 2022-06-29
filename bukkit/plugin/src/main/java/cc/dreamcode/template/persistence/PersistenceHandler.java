package cc.dreamcode.template.persistence;

import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.Validate;

@Getter
@Setter
@RequiredArgsConstructor
public class PersistenceHandler {

    private DocumentPersistence databasePersistence;
    private DocumentPersistence inMemoryPersistence;

    public void registerCollection(@NonNull PersistenceCollection collection) {
        Validate.notNull(this.databasePersistence, "DatabasePersistence cannot be null!");
        Validate.notNull(this.inMemoryPersistence, "InMemoryPersistence cannot be null!");

        this.databasePersistence.registerCollection(collection);
        this.inMemoryPersistence.registerCollection(collection);
    }

}
