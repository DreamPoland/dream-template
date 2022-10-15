package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class DocumentRepositoryComponentClassResolver extends ComponentClassResolver<Class<? extends DocumentRepository>> {

    private @Inject TemplatePlugin templatePlugin;
    private @Inject DocumentPersistence documentPersistence;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends DocumentRepository> documentRepositoryClass) {
        return DocumentRepository.class.isAssignableFrom(documentRepositoryClass);
    }

    @Override
    public String getComponentName() {
        return "repository";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends DocumentRepository> documentRepositoryClass) {
        return new HashMap<>();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends DocumentRepository> documentRepositoryClass) {
        PersistenceCollection persistenceCollection = PersistenceCollection.of(documentRepositoryClass);
        this.documentPersistence.registerCollection(persistenceCollection);

        return RepositoryDeclaration.of(documentRepositoryClass)
                .newProxy(
                        this.documentPersistence,
                        persistenceCollection,
                        this.templatePlugin.getClass().getClassLoader()
                );
    }
}
