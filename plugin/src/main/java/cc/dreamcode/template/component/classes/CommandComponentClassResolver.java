package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.features.command.CommandHandler;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

public class CommandComponentClassResolver extends ComponentClassResolver<Class<? extends CommandHandler>> {

    private @Inject TemplatePlugin templatePlugin;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends CommandHandler> aClass) {
        return CommandHandler.class.isAssignableFrom(aClass);
    }

    @Override
    public String getComponentName() {
        return "command";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends CommandHandler> commandHandlerClass) {
        final CommandHandler commandHandler = injector.createInstance(commandHandlerClass);
        return new ImmutableMap.Builder<String, Object>()
                .put("name", commandHandler.getName())
                .put("aliases", commandHandler.getAliases())
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends CommandHandler> commandHandlerClass) {
        final CommandHandler commandHandler = injector.createInstance(commandHandlerClass);
        SimpleCommandMap simpleCommandMap = this.getSimpleCommandMap(this.templatePlugin.getServer());

        if (simpleCommandMap == null) {
            return commandHandler;
        }

        simpleCommandMap.register(this.templatePlugin.getDescription().getName(), commandHandler);
        return commandHandler;
    }

    private SimpleCommandMap getSimpleCommandMap(Server server) {
        SimplePluginManager spm = (SimplePluginManager) server.getPluginManager();

        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assert f != null;
        f.setAccessible(true);

        try {
            return (SimpleCommandMap) f.get(spm);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
