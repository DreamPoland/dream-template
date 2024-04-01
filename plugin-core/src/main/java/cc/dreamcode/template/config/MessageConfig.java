package cc.dreamcode.template.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.adventure.bukkit.AdventureBukkitNotice;
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
    public AdventureBukkitNotice usage = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&7Przyklady uzycia komendy: &c{label}");
    @CustomKey("command-usage-help")
    public AdventureBukkitNotice usagePath = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&f{usage} &8- &7{description}");

    @CustomKey("command-usage-not-found")
    public AdventureBukkitNotice usageNotFound = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie znaleziono pasujacych do kryteriow komendy.");
    @CustomKey("command-path-not-found")
    public AdventureBukkitNotice pathNotFound = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cTa komenda jest pusta lub nie posiadasz dostepu do niej.");
    @CustomKey("command-no-permission")
    public AdventureBukkitNotice noPermission = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnien.");
    @CustomKey("command-not-player")
    public AdventureBukkitNotice notPlayer = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu gracza.");
    @CustomKey("command-not-console")
    public AdventureBukkitNotice notConsole = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu konsoli.");
    @CustomKey("command-invalid-format")
    public AdventureBukkitNotice invalidFormat = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodano nieprawidlowy format argumentu komendy. ({input})");

    @CustomKey("player-not-found")
    public AdventureBukkitNotice playerNotFound = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodanego gracza nie znaleziono.");
    @CustomKey("world-not-found")
    public AdventureBukkitNotice worldNotFound = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodanego swiata nie znaleziono.");
    @CustomKey("cannot-do-at-my-self")
    public AdventureBukkitNotice cannotDoAtMySelf = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie mozesz tego zrobic na sobie.");
    @CustomKey("number-is-not-valid")
    public AdventureBukkitNotice numberIsNotValid = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodana liczba nie jest cyfra.");

}
