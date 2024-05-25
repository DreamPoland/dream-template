package cc.dreamcode.template.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Async;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.notice.minecraft.adventure.bukkit.AdventureBukkitNotice;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.utilities.Formatter;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@Command(name = "example")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExampleCommand implements CommandBase {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Async
    @Permission("dream-template.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    AdventureBukkitNotice reload(CommandSender sender) {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            return this.messageConfig.reloaded
                    .with("time", Formatter.format(System.currentTimeMillis() - time));
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            return this.messageConfig.reloadError
                    .with("error", e.getMessage());
        }
    }
}
