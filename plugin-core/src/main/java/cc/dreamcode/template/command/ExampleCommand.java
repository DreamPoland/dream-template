package cc.dreamcode.template.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Async;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import cc.dreamcode.utilities.Formatter;
import cc.dreamcode.utilities.builder.MapBuilder;
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
    void reload(CommandSender sender) {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageConfig.reloaded.send(sender, new MapBuilder<String, Object>()
                    .put("time", Formatter.format(System.currentTimeMillis() - time))
                    .build());
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            this.messageConfig.reloadError.send(sender, new MapBuilder<String, Object>()
                    .put("error", e.getMessage())
                    .build());
        }
    }
}
