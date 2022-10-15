package cc.dreamcode.template.component;

import cc.dreamcode.template.component.classes.*;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.features.command.builder.ListBuilder;
import eu.okaeri.injector.Injector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public final class ComponentHandler {

    private final Injector injector;
    private final List<Class<? extends ComponentClassResolver>> classResolvers = new ListBuilder<Class<? extends ComponentClassResolver>>()
            .add(ConfigurationComponentClassResolver.class)
            .add(DocumentRepositoryComponentClassResolver.class)
            .add(DocumentPersistenceComponentClassResolver.class)
            .add(CommandComponentClassResolver.class)
            .add(ListenerComponentClassResolver.class)
            .add(RunnableComponentClassResolver.class)
            .add(ObjectComponentClassResolver.class)
            .build();

    /**
     * This method can register all content of this plugin.
     * When class is undefined, object will be bound for injection only.
     * Class with constructor can only be register with RegisterObject method.
     *
     * @param componentClass class to register & bind
     * @param consumer       apply changes after register.
     */
    @SuppressWarnings("ALL")
    public <T> void registerComponent(@NonNull Class<T> componentClass, Consumer<T> consumer) {
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
                    return;
                }
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method can register all content of this plugin.
     * When class is undefined, object will be bound for injection only.
     * Class with constructor can only be register with RegisterObject method.
     *
     * @param componentClass class to register & bind
     */
    public void registerComponent(@NonNull Class<?> componentClass) {
        this.registerComponent(componentClass, null);
    }

}
