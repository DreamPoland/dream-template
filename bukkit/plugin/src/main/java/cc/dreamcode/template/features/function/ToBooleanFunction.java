package cc.dreamcode.template.features.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    Boolean applyAsBoolean(T value);
}
