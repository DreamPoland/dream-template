package cc.dreamcode.template.features.builder;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapBuilder<K, V> {

    private final Map<K, V> map = new HashMap<>();

    public MapBuilder<K, V> put(K k, V v) {
        this.map.put(k, v);
        return this;
    }

    public Map<K, V> build() {
        return this.map;
    }

}
