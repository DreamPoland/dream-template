package cc.dreamcode.template.component.resolver;


import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.exception.PluginRuntimeException;
import cc.dreamcode.template.stereotypes.Scheduler;
import eu.okaeri.injector.Injector;

public class RunnableComponentResolver implements ComponentResolver<Runnable> {

    @Override
    public void resolve(PluginMain plugin, Injector injector, Runnable runnable) {
        long start = System.currentTimeMillis();

        Scheduler scheduler = runnable.getClass().getAnnotation(Scheduler.class);
        if (scheduler == null) {
            throw new PluginRuntimeException("Runnable are not have a Scheduler annotation.");
        }

        if (scheduler.async()) {
            plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                    injector.createInstance(runnable.getClass()), scheduler.start(), scheduler.repeat());
        }
        else {
            plugin.getServer().getScheduler().runTaskTimer(plugin,
                    injector.createInstance(runnable.getClass()), scheduler.start(), scheduler.repeat());
        }

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie dodano repeat-task")
                        .name(runnable.getClass().getSimpleName())
                        .took(took)
                        .meta("delay-time", scheduler.start())
                        .meta("repeat-time", scheduler.repeat())
                        .meta("async", scheduler.async())
                        .build()
        );
    }

}
