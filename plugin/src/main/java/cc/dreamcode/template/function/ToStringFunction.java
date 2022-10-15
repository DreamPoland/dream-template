package cc.dreamcode.template.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
