package cc.dreamcode.template.features;

import java.util.Optional;
import java.util.Set;

/**
 * Manager interface for basic inMemory data structure.
 * @param <K> key
 * @param <V> value
 */
public interface Manager<K, V> {

    Optional<V> getByKey(K k);

    Set<V> getSet();

    V add(V v);

    void remove(K k);

}