package cc.dreamcode.template.features.hook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PluginHookType {
    FUNNY_GUILDS("FunnyGuilds", "net.dzikoysk.funnyguilds.FunnyGuilds");

    private final String name;
    private final String classPackageName;

}
