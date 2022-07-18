package cc.dreamcode.template.features.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<F, S> {

    private final F first;
    private final S second;

    public static <F, S> Pair<F, S> of(F f, S s) {
        return new Pair<>(f, s);
    }

}
