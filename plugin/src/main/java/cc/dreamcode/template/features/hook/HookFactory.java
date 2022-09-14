package cc.dreamcode.template.features.hook;

import cc.dreamcode.template.PluginLogger;
import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.exception.PluginRuntimeException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HookFactory {

    private @Inject PluginMain pluginMain;

    public void tryLoadAllDepends(@NonNull List<Class<? extends PluginHook>> pluginHookList, @NonNull HookManager hookManager) {
        long start = System.currentTimeMillis();

        pluginHookList.forEach(pluginHookClass -> {
            final PluginHook pluginHook = this.pluginMain.createInstance(pluginHookClass);
            hookManager.add(pluginHook);

            try {
                Class.forName(pluginHook.getPluginHookType().getClassPackageName());

                if (!this.pluginMain.getDescription().getSoftDepend().contains(pluginHook.getPluginHookType().getName())) {
                    throw new PluginRuntimeException("Plugin (name=" + pluginHook.getPluginHookType().getName() + ") " +
                            "is not a softdepend. Add plugin name to file/main class.");
                }

                pluginHook.tryInit();
            }
            catch (ClassNotFoundException e) {
                PluginMain.getPluginLogger().warning("Plugin (name=" + pluginHook.getPluginHookType().getName() + ") " +
                        "not found or version is not compatible. Plugin works with limitations.");
            }
        });

        if (hookManager.getSet()
                .stream()
                .noneMatch(PluginHook::isInitialized)) {
            return;
        }

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Loaded soft-depends")
                        .name(hookManager.getSet().stream()
                                .filter(PluginHook::isInitialized)
                                .map(pluginHook -> pluginHook.getPluginHookType().getName())
                                .collect(Collectors.joining(", ")))
                        .took(took)
                        .build()
        );
    }
}
