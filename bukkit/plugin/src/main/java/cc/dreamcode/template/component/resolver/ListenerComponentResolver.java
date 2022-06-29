package cc.dreamcode.template.component.resolver;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import eu.okaeri.injector.Injector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ListenerComponentResolver implements ComponentResolver<Listener> {

    @Override
    public void resolve(PluginMain plugin, Injector injector, Listener listener) {
        long start = System.currentTimeMillis();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(injector.createInstance(listener.getClass()), plugin);

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie dodano listener (event)")
                        .name(listener.getClass().getSimpleName())
                        .took(took)
                        .meta("methods", Arrays.stream(listener.getClass().getDeclaredMethods())
                                .filter(method -> method.getAnnotation(EventHandler.class) != null)
                                .map(Method::getName)
                                .collect(Collectors.toList()))
                        .build()
        );
    }

}
