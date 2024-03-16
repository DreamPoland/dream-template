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

    @CustomKey(value = "usage")
    public AdventureBukkitNotice usage = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &5{usage}");
    @CustomKey(value = "no-permission")
    public AdventureBukkitNotice noPermission = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnien.");
    @CustomKey(value = "not-player")
    public AdventureBukkitNotice notPlayer = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie jestes graczem aby to zrobic.");

    @CustomKey(value = "player-not-found")
    public AdventureBukkitNotice playerNotFound = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodanego gracza nie znaleziono.");
    @CustomKey(value = "cannot-do-at-my-self")
    public AdventureBukkitNotice cannotDoAtMySelf = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cNie mozesz tego zrobic na sobie.");
    @CustomKey(value = "number-is-not-valid")
    public AdventureBukkitNotice numberIsNotValid = new AdventureBukkitNotice(MinecraftNoticeType.CHAT, "&cPodana liczba nie jest cyfra.");

}
