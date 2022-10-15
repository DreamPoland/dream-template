package cc.dreamcode.template.model.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    boolean isInitialized();

    void tryInit();

}
