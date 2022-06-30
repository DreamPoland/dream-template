package cc.dreamcode.template.features.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    boolean isInitialized();

    void tryInit();

}
