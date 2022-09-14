package cc.dreamcode.template.features.hook;

import cc.dreamcode.template.exception.PluginRuntimeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPluginHook implements PluginHook {

    private boolean initialized = false;

    @Override
    public void tryInit() {
        try {
            this.callInit();
            this.initialized = true;
        }
        catch (Exception e) {
            this.initialized = false;
            throw new PluginRuntimeException("Plugin (name=" + this.getPluginHookType().getName() + ") " +
                    "throw an error. Hook process is cancelled.", e);
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    public abstract void callInit();
}
