package cc.dreamcode.template;

import cc.dreamcode.template.boot.PluginBootLoader;
import cc.dreamcode.template.boot.PluginFactory;
import cc.dreamcode.template.component.ComponentHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.template.features.hook.HookFactory;
import cc.dreamcode.template.features.hook.HookManager;
import cc.dreamcode.template.features.hook.plugins.FunnyGuildsHook;
import cc.dreamcode.template.features.menu.MenuActionHandler;
import cc.dreamcode.template.features.nms.NmsFactory;
import cc.dreamcode.template.features.user.UserController;
import cc.dreamcode.template.features.user.UserRepository;
import cc.dreamcode.template.features.user.UserService;
import eu.okaeri.injector.Injector;
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
        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin. (A-Z)
        componentHandler.registerObject(BukkitTasker.newPool(this));
        componentHandler.registerObject(NmsFactory.getNmsAccessor());
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(DocumentPersistence.class);
        componentHandler.registerComponent(UserRepository.class);
        componentHandler.registerComponent(UserService.class);
        componentHandler.registerComponent(HookManager.class, hookManager ->
                this.createInstance(HookFactory.class).tryLoadAllDepends(Stream.of(
                        FunnyGuildsHook.class
                ).collect(Collectors.toList()), hookManager));
        componentHandler.registerComponent(UserController.class);
        componentHandler.registerComponent(MenuActionHandler.class);
    }

    @Override
    public void stop(@NonNull Injector injector) {
        // features need to be call by stop server
    }
}
