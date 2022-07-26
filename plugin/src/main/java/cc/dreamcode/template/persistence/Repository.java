package cc.dreamcode.template.persistence;

import java.util.Optional;
import java.util.Set;

public interface Repository<K, V> {

    RepositoryLoader getRepositoryLoader();

    boolean contains(K k);

    Optional<V> getByKey(K k);

    Set<V> getSet();

    V get(K k, boolean create);

    void remove(K k);

}
