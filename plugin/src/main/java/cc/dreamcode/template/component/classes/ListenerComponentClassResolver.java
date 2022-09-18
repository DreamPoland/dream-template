package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ListenerComponentClassResolver extends ComponentClassResolver<Class<? extends Listener>> {

    private @Inject TemplatePlugin templatePlugin;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends Listener> listenerClass) {
        return Listener.class.isAssignableFrom(listenerClass);
    }

    @Override
    public String getComponentName() {
        return "listener (event)";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends Listener> listenerClass) {
        return new ImmutableMap.Builder<String, Object>()
                .put("events", Arrays.stream(listenerClass.getDeclaredMethods())
                        .filter(method -> method.getAnnotation(EventHandler.class) != null)
                        .map(Method::getName)
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends Listener> listenerClass) {
        final Listener listener = injector.createInstance(listenerClass);

        final PluginManager pm = this.templatePlugin.getServer().getPluginManager();
        pm.registerEvents(listener, this.templatePlugin);

        return listener;
    }
}
