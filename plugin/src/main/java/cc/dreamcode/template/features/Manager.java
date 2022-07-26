package cc.dreamcode.template.features;

import java.util.Optional;
import java.util.Set;

public interface Manager<K, V> {

    Optional<V> getByKey(K k);

    Set<V> getSet();

    void add(V v);

    void remove(K k);

}
