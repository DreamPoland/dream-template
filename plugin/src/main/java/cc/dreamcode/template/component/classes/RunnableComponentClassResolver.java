package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.component.annotations.Scheduler;
import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.exception.PluginRuntimeException;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.util.Map;

public class RunnableComponentClassResolver extends ComponentClassResolver<Class<? extends Runnable>> {

    private @Inject TemplatePlugin templatePlugin;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends Runnable> runnableClass) {
        return Runnable.class.isAssignableFrom(runnableClass);
    }

    @Override
    public String getComponentName() {
        return "runnable";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends Runnable> runnableClass) {
        Scheduler scheduler = runnableClass.getAnnotation(Scheduler.class);
        if (scheduler == null) {
            throw new PluginRuntimeException("Runnable are not have a Scheduler annotation.");
        }

        return new ImmutableMap.Builder<String, Object>()
                .put("async", scheduler.async())
                .put("start-time", scheduler.delay())
                .put("interval-time", scheduler.interval())
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends Runnable> runnableClass) {
        final Runnable runnable = injector.createInstance(runnableClass);

        Scheduler scheduler = runnable.getClass().getAnnotation(Scheduler.class);
        if (scheduler == null) {
            throw new PluginRuntimeException("Runnable are not have a Scheduler annotation.");
        }

        if (scheduler.async()) {
            this.templatePlugin.getServer().getScheduler().runTaskTimerAsynchronously(this.templatePlugin,
                    runnable, scheduler.delay(), scheduler.interval());
        }
        else {
            this.templatePlugin.getServer().getScheduler().runTaskTimer(this.templatePlugin,
                    runnable, scheduler.delay(), scheduler.interval());
        }

        return runnable;
    }

}
