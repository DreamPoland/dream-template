package cc.dreamcode.template.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bungee.BungeeNotice;
import cc.dreamcode.platform.bungee.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Template (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BungeeNotice usage = new BungeeNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BungeeNotice noPermission = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BungeeNotice noPlayer = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BungeeNotice playerIsOffline = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Podany gracz &cjest offline.");
    public BungeeNotice notPlayer = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BungeeNotice notNumber = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BungeeNotice playerIsMe = new BungeeNotice(MinecraftNoticeType.CHAT, "&4Nie rob tego &cna sobie.");
    

}
