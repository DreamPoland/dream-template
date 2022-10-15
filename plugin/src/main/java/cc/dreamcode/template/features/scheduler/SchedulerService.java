/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package cc.dreamcode.template.features.scheduler;

import cc.dreamcode.template.TemplatePlugin;
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
