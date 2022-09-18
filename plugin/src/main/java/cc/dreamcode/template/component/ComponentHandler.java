package cc.dreamcode.template.component;

import cc.dreamcode.template.component.classes.*;
import cc.dreamcode.template.component.objects.GenericComponentObjectResolver;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.component.resolvers.ComponentObjectResolver;
import com.google.common.collect.ImmutableList;
import eu.okaeri.injector.Injector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public final class ComponentHandler {

    private final Injector injector;
    private final List<Class<? extends ComponentClassResolver>> classResolvers = new ImmutableList.Builder<Class<? extends ComponentClassResolver>>()
            .add(ConfigurationComponentClassResolver.class)
            .add(DocumentPersistenceComponentClassResolver.class)
            .add(DocumentRepositoryComponentClassResolver.class)
            .add(PersistenceServiceComponentClassResolver.class)
            .add(CommandComponentClassResolver.class)
            .add(ListenerComponentClassResolver.class)
            .add(RunnableComponentClassResolver.class)
            .add(ObjectComponentClassResolver.class)
            .build();

    private final List<Class<? extends ComponentObjectResolver>> objectResolvers = new ImmutableList.Builder<Class<? extends ComponentObjectResolver>>()
            .add(GenericComponentObjectResolver.class)
            .build();

    /**
     * This method can register all content of this plugin.
     * When class is undefined, object will be bound for injection only.
     * Class with constructor can only be register with RegisterObject method.
     *
     * @param componentClass class to register & bind
     * @param consumer apply changes after register.
     */
    @SuppressWarnings("ALL")
    public <T> ComponentHandler registerComponent(@NonNull Class<T> componentClass, Consumer<T> consumer) {
        for (Class<? extends ComponentClassResolver> componentResolvers : this.classResolvers) {
            try {
                final ComponentClassResolver componentClassResolver = componentResolvers.newInstance();
                if (componentClassResolver.isAssignableFrom(componentClass)) {
                    this.injector.injectFields(componentClassResolver);
                    if (consumer != null) {
                        consumer.accept((T) componentClassResolver.process(this.injector, componentClass));
                    }
                    else {
                        componentClassResolver.process(this.injector, componentClass);
                    }
                    return this;
                }
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /**
     * This method can register all content of this plugin.
     * When class is undefined, object will be bound for injection only.
     * Class with constructor can only be register with RegisterObject method.
     *
     * @param componentClass class to register & bind
     */
    public ComponentHandler registerComponent(@NonNull Class<?> componentClass) {
        return this.registerComponent(componentClass, null);
    }

    /**
     * This method can register any existing objects of this plugin.
     * RegisterObject method does not bind injected fields etc.
     *
     * @param t object to bind
     * @param consumer apply changes after bind.
     */
    @SuppressWarnings("ALL")
    public <T> ComponentHandler registerObject(@NonNull T t, Consumer<T> consumer) {
        for (Class<? extends ComponentObjectResolver> componentResolvers : this.objectResolvers) {
            try {
                final ComponentObjectResolver componentObjectResolver = componentResolvers.newInstance();
                if (componentObjectResolver.isAssignableFrom(t.getClass())) {
                    this.injector.injectFields(componentObjectResolver);
                    if (consumer != null) {
                        consumer.accept((T) componentObjectResolver.process(this.injector, t));
                    }
                    else {
                        componentObjectResolver.process(this.injector, t);
                    }
                    return this;
                }
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * This method can register any existing objects of this plugin.
     * RegisterObject method does not bind injected fields etc.
     *
     * @param object object to bind
     */
    public ComponentHandler registerObject(@NonNull Object object) {
        return this.registerObject(object, null);
    }

}
