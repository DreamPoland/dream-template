package cc.dreamcode.template.features.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<F, S> {

    private final F first;
    private final S second;

}
