package cc.dreamcode.template.features.scheduler;

import cc.dreamcode.template.TemplatePlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Scheduler from LuckPerms <3
public class SchedulerService {

    private @Inject TemplatePlugin templatePlugin;

    @Getter @Setter private ScheduledThreadPoolExecutor scheduler;
    @Getter @Setter private ForkJoinPool async;
    @Getter @Setter private Executor sync;

    public SchedulerTask asyncLater(@NonNull Runnable task, long delay, TimeUnit unit) {
        ScheduledFuture<?> future = this.scheduler.schedule(() -> this.async.execute(task), delay, unit);
        return () -> future.cancel(false);
    }

    public SchedulerTask asyncRepeating(@NonNull Runnable task, long start, long interval, TimeUnit unit) {
        ScheduledFuture<?> future = this.scheduler.scheduleAtFixedRate(() -> this.async.execute(task), start, interval, unit);
        return () -> future.cancel(false);
    }

    public void shutdownScheduler() {
        this.scheduler.shutdown();
        try {
            if (!this.scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                TemplatePlugin.getTemplateLogger().warning("Timed out waiting for the dream-template scheduler to terminate");
                reportRunningTasks(thread -> thread.getName().equals("dream-template-scheduler"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdownExecutor() {
        this.async.shutdown();
        try {
            if (!this.async.awaitTermination(1, TimeUnit.MINUTES)) {
                TemplatePlugin.getTemplateLogger().warning("Timed out waiting for the dream-template worker thread pool to terminate");
                reportRunningTasks(thread -> thread.getName().startsWith("dream-template-worker-"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reportRunningTasks(Predicate<Thread> predicate) {
        Thread.getAllStackTraces().forEach((thread, stack) -> {
            if (predicate.test(thread)) {
                TemplatePlugin.getTemplateLogger().error("Thread " + thread.getName() + " is blocked, and may be the reason for the slow shutdown!\n" +
                        Arrays.stream(stack).map(el -> "  " + el).collect(Collectors.joining("\n"))
                );
            }
        });
    }

    public static final class WorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
        private static final AtomicInteger atomicInteger = new AtomicInteger(0);

        @Override
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            thread.setDaemon(true);
            thread.setName("dream-worker-" + atomicInteger.getAndIncrement());
            return thread;
        }
    }

    public static final class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            TemplatePlugin.getTemplateLogger().error("Thread " + t.getName() + " threw an uncaught exception", e);
        }
    }

}
