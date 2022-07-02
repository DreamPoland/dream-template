package cc.dreamcode.template.commands.admin;

import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.commands.CommandUse;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.persistence.RepositoryLoader;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PluginCMD extends CommandUse {

    private @Inject PluginMain pluginMain;
    private @Inject MessageConfig messageConfig;

    public PluginCMD() {
        super("plugintemplate", null);
    }

    @Override
    public void run(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission("rpl." + this.getName())) {
            this.send(this.messageConfig.noPermission, sender);
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("load-data")) {
                this.pluginMain.getPersistenceService().getPersistenceHandler().getRepositoryLoaderList()
                        .forEach(RepositoryLoader::load);

                this.send(this.messageConfig.dataLoaded, sender);
                return;
            }
        }

        this.send(this.messageConfig.usage, sender, new ImmutableMap.Builder<String, Object>()
                .put("usage", "/plugin [load-data]")
                .build());
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
