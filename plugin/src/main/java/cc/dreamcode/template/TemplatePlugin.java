package cc.dreamcode.template;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.template.boot.PluginBootLoader;
import cc.dreamcode.template.component.ComponentHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.model.hook.HookFactory;
import cc.dreamcode.template.model.hook.HookManager;
import cc.dreamcode.template.model.hook.plugins.FunnyGuildsHook;
import cc.dreamcode.template.nms.NmsFactory;
import cc.dreamcode.template.scheduler.SchedulerService;
import cc.dreamcode.template.model.user.UserRepository;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Plugin(name = "Dream-Template", version = "1.0-SNAPSHOT")
@Author("Ravis96")
@Description("Template plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

// If are you using plugin-hooks, add them here via soft-dependency as well
@SoftDependency("FunnyGuilds")
public final class TemplatePlugin extends PluginBootLoader {

    @Getter private static TemplatePlugin templatePlugin;
    @Getter private static TemplateLogger templateLogger;

    @Override
    public void load() {
        // Static content for api.
        templatePlugin = this;
        templateLogger = new TemplateLogger(templatePlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        // Injectable object registering. (library etc.)
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(NmsFactory.getNmsAccessor());
        this.registerInjectable(BukkitMenuProvider.create(this));

        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin. (A-Z)
        componentHandler.registerComponent(SchedulerService.class, schedulerService -> {
            schedulerService.setScheduler(new ScheduledThreadPoolExecutor(1, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("dream-template-scheduler");
                return thread;
            }));

            schedulerService.getScheduler().setRemoveOnCancelPolicy(true);
            schedulerService.getScheduler().setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            schedulerService.setAsync(new ForkJoinPool(
                    16,
                    new SchedulerService.WorkerThreadFactory(),
                    new SchedulerService.ExceptionHandler(),
                    false
            ));
        });
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(DocumentPersistence.class);
        componentHandler.registerComponent(UserRepository.class);
        componentHandler.registerComponent(HookManager.class, hookManager ->
                this.createInstance(HookFactory.class).tryLoadAllDepends(Stream.of(
                        FunnyGuildsHook.class
                ).collect(Collectors.toList()), hookManager));
    }

    @Override
    public void stop() {
        // features need to be call by stop server
    }
}
