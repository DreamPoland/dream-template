package cc.dreamcode.template.model.hook;

import cc.dreamcode.template.model.Manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HookManager implements Manager<PluginHookType, PluginHook> {

    private final Map<PluginHookType, PluginHook> pluginHooks = new HashMap<>();

    @Override
    public Optional<PluginHook> getByKey(PluginHookType pluginHookType) {
        return Optional.ofNullable(this.pluginHooks.get(pluginHookType));
    }

    @Override
    public Set<PluginHook> getSet() {
        return new HashSet<>(this.pluginHooks.values());
    }

    @Override
    public PluginHook add(PluginHook pluginHook) {
        return this.pluginHooks.put(pluginHook.getPluginHookType(), pluginHook);
    }

    @Override
    public void remove(PluginHookType pluginHookType) {
        this.pluginHooks.remove(pluginHookType);
    }
}
