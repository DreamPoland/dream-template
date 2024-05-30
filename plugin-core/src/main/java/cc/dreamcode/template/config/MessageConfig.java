package cc.dreamcode.template.config;

import cc.dreamcode.notice.adventure.BukkitNotice;
import cc.dreamcode.notice.minecraft.NoticeType;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Template (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
public class MessageConfig extends OkaeriConfig {

    @CustomKey("command-usage")
    public BukkitNotice usage = new BukkitNotice(NoticeType.CHAT, "&7Przyklady uzycia komendy: &c{label}");
    @CustomKey("command-usage-help")
    public BukkitNotice usagePath = new BukkitNotice(NoticeType.CHAT, "&f{usage} &8- &7{description}");

    @CustomKey("command-usage-not-found")
    public BukkitNotice usageNotFound = new BukkitNotice(NoticeType.CHAT, "&cNie znaleziono pasujacych do kryteriow komendy.");
    @CustomKey("command-path-not-found")
    public BukkitNotice pathNotFound = new BukkitNotice(NoticeType.CHAT, "&cTa komenda jest pusta lub nie posiadasz dostepu do niej.");
    @CustomKey("command-no-permission")
    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&cNie posiadasz uprawnien.");
    @CustomKey("command-not-player")
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu gracza.");
    @CustomKey("command-not-console")
    public BukkitNotice notConsole = new BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu konsoli.");
    @CustomKey("command-invalid-format")
    public BukkitNotice invalidFormat = new BukkitNotice(NoticeType.CHAT, "&cPodano nieprawidlowy format argumentu komendy. ({input})");

    @CustomKey("player-not-found")
    public BukkitNotice playerNotFound = new BukkitNotice(NoticeType.CHAT, "&cPodanego gracza nie znaleziono.");
    @CustomKey("world-not-found")
    public BukkitNotice worldNotFound = new BukkitNotice(NoticeType.CHAT, "&cPodanego swiata nie znaleziono.");
    @CustomKey("cannot-do-at-my-self")
    public BukkitNotice cannotDoAtMySelf = new BukkitNotice(NoticeType.CHAT, "&cNie mozesz tego zrobic na sobie.");
    @CustomKey("number-is-not-valid")
    public BukkitNotice numberIsNotValid = new BukkitNotice(NoticeType.CHAT, "&cPodana liczba nie jest cyfra.");

    @CustomKey("config-reloaded")
    public BukkitNotice reloaded = new BukkitNotice(NoticeType.CHAT, "&aPrzeladowano! &7({time})");
    @CustomKey("config-reload-error")
    public BukkitNotice reloadError = new BukkitNotice(NoticeType.CHAT, "&cZnaleziono problem w konfiguracji: &6{error}");

}
