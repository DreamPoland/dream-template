package cc.dreamcode.template.features.hook.plugins;

import cc.dreamcode.template.features.hook.AbstractPluginHook;
import cc.dreamcode.template.features.hook.PluginHookType;

public class FunnyGuildsHook extends AbstractPluginHook {

    //@Getter private FunnyGuilds funnyGuilds;

    @Override
    public void callInit() {
        // get funnyguids -- empty, beacuse its only example
        // add plugin version check -- authors can add changes that may destroy plugin code (throw error with PluginRuntimeException)

        //this.funnyGuilds = FunnyGuilds.getInstance();

        //if (!this.funnyGuilds.getVersion().getMainVersion().equals("4.10.1")) {
        //    throw new PluginRuntimeException("Plugin (name=" + this.getPluginHookType().getName() + ") musi posiadac wersje 4.10.1.");
        //}
    }

    @Override
    public PluginHookType getPluginHookType() {
        return PluginHookType.FUNNY_GUILDS;
    }
}
