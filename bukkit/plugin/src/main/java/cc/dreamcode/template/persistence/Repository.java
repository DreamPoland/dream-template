package cc.dreamcode.template.persistence;

import eu.okaeri.persistence.repository.DocumentRepository;

import java.util.Optional;
import java.util.Set;

public interface Repository<REPO extends DocumentRepository, K, V> {

    RepositoryLoader<REPO> getRepositoryLoader();

    boolean contains(K k);

    Optional<V> getByKey(K k);

    Set<V> getSet();

    V getOrCreate(K t);

    void remove(K k);

}
