package cc.dreamcode.template.features.command;

import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.persistence.RepositoryLoader;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PluginCommand extends CommandUse {

    private @Inject PluginMain pluginMain;
    private @Inject MessageConfig messageConfig;

    public PluginCommand() {
        super("plugincase", null);
    }

    @Override
    public void run(@NonNull CommandSender sender, @NonNull String[] args) {
        whenNot(sender.hasPermission("rpl." + this.getName()), this.messageConfig.noPermission);
        whenNot(args.length == 1 &&
                args[0].equalsIgnoreCase("load-data"), this.messageConfig.usage, new ImmutableMap.Builder<String, Object>()
                .put("usage", "/plugin [load-data]")
                .build());

        this.pluginMain.getPersistenceService().getPersistenceHandler().getRepositoryLoaderList()
                .forEach(RepositoryLoader::load);

        this.send(this.messageConfig.dataLoaded, sender);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (!player.hasPermission("rpl." + this.getName())) {
            return null;
        }

        if (args.length == 1) {
            return Collections.singletonList("load-data");
        }

        return null;
    }
}