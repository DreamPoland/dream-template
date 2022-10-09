package cc.dreamcode.template.features.builder;

import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
public class HashMapBuilder<K, V> {

    private final HashMap<K, V> map = new HashMap<>();

    public HashMapBuilder<K, V> put(K k, V v) {
        this.map.put(k, v);
        return this;
    }

    public HashMap<K, V> build() {
        return this.map;
    }

}
