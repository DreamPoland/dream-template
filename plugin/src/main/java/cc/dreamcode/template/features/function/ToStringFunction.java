package cc.dreamcode.template.features.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
