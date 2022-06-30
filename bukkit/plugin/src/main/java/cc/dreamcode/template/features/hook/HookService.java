package cc.dreamcode.template.features.hook;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.exception.PluginRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HookService {

    private final Plugin plugin;
    @Getter private final HookManager hookManager = new HookManager();

    public void tryLoadAllDepends(List<PluginHook> pluginHookList) {
        long start = System.currentTimeMillis();

        pluginHookList.forEach(pluginHook -> {
            this.hookManager.add(pluginHook);

            try {
                Class.forName(pluginHook.getPluginHookType().getClassPackageName());

                if (!this.plugin.getDescription().getSoftDepend().contains(pluginHook.getPluginHookType().getName())) {
                    throw new PluginRuntimeException("Plugin (name=" + pluginHook.getPluginHookType().getName() + ") " +
                            "is not a softdepend. Add plugin name to file/main class.");
                }

                pluginHook.tryInit();
            }
            catch (ClassNotFoundException e) {
                PluginMain.getPluginLogger().warning("Plugin (name=" + pluginHook.getPluginHookType().getName() + ") " +
                        "nie zostal znaleziony lub wersja jest niekompatybilna! Plugin dziala z ograniczeniami.");
            }
        });

        if (this.hookManager.getSet()
                .stream()
                .noneMatch(PluginHook::isInitialized)) {
            return;
        }

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie zaladowano kompatybilne pluginy")
                        .name(this.hookManager.getSet().stream()
                                .filter(PluginHook::isInitialized)
                                .map(pluginHook -> pluginHook.getPluginHookType().getName())
                                .collect(Collectors.joining(", ")))
                        .took(took)
                        .build()
        );
    }
}
