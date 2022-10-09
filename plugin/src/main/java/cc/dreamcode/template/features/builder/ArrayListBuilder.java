package cc.dreamcode.template.features.builder;

import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class ArrayListBuilder<T> {

    private final ArrayList<T> list = new ArrayList<>();

    public ArrayListBuilder<T> add(T t) {
        this.list.add(t);
        return this;
    }

    public ArrayList<T> build() {
        return this.list;
    }

}
