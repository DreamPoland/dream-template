package cc.dreamcode.template.features.hook;

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
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    public abstract void callInit();
}
