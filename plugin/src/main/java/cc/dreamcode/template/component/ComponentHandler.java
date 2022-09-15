package cc.dreamcode.template.component;

import cc.dreamcode.template.component.resolvers.*;
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
    private final List<Class<? extends ComponentResolver>> componentResolvers = new ImmutableList.Builder<Class<? extends ComponentResolver>>()
            .add(ConfigurationComponentResolver.class)
            .add(DocumentPersistenceComponentResolver.class)
            .add(DocumentRepositoryComponentResolver.class)
            .add(PersistenceServiceComponentResolver.class)
            .add(CommandComponentResolver.class)
            .add(ListenerComponentResolver.class)
            .add(RunnableComponentResolver.class)
            .add(ObjectComponentResolver.class)
            .build();

    @SuppressWarnings("unchecked")
    public <T> ComponentHandler registerComponent(@NonNull Class<T> componentClass, Consumer<T> consumer) {
        for (Class<? extends ComponentResolver> componentResolvers : this.componentResolvers) {
            try {
                final ComponentResolver componentResolver = componentResolvers.newInstance();
                if (componentResolver.isAssignableFrom(componentClass)) {
                    this.injector.injectFields(componentResolver);
                    if (consumer != null) {
                        final Object object = componentResolver.process(this.injector, componentClass);
                        consumer.accept((T) object);
                    }
                    else {
                        componentResolver.process(this.injector, componentClass);
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

    public ComponentHandler registerComponent(@NonNull Class<?> componentClass) {
        return this.registerComponent(componentClass, null);
    }

}
