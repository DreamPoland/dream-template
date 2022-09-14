package cc.dreamcode.template.component.resolvers;


import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.component.ComponentResolver;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.stereotypes.Scheduler;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.util.Map;

public class RunnableComponentResolver extends ComponentResolver<Class<? extends Runnable>> {

    private @Inject PluginMain pluginMain;

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
                .put("delay-time", scheduler.delay())
                .put("repeat-time", scheduler.repeat())
                .put("async", scheduler.async())
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
            this.pluginMain.getServer().getScheduler().runTaskTimerAsynchronously(this.pluginMain,
                    runnable, scheduler.delay(), scheduler.repeat());
        }
        else {
            this.pluginMain.getServer().getScheduler().runTaskTimer(this.pluginMain,
                    runnable, scheduler.delay(), scheduler.repeat());
        }

        return runnable;
    }

}
