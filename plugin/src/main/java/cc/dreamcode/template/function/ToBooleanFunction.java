package cc.dreamcode.template.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    Boolean applyAsBoolean(T value);
}
