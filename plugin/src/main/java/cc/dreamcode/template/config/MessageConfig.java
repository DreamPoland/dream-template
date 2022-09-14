package cc.dreamcode.template.config;

import cc.dreamcode.template.features.notice.Notice;
import cc.dreamcode.template.stereotypes.Configuration;
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
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice noPlayer = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public Notice playerIsOffline = new Notice(Notice.Type.CHAT, "&4Podany gracz &cjest offline.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");
    public Notice notNumber = new Notice(Notice.Type.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public Notice playerIsMe = new Notice(Notice.Type.CHAT, "&4Nie rob tego &cna sobie.");
    public Notice amountIsZero = new Notice(Notice.Type.CHAT, "&cPodana liczba nie moze wynosic 0 oraz nie moze byc liczba negatywna.");
    public Notice dataLoaded = new Notice(Notice.Type.CHAT, "&cData zostalo zaladowane z database!");



}
