package cc.dreamcode.template.features.command.builder;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ListBuilder<T> {

    private final List<T> list = new ArrayList<>();

    public ListBuilder<T> add(T t) {
        this.list.add(t);
        return this;
    }

    public List<T> build() {
        return this.list;
    }

}
