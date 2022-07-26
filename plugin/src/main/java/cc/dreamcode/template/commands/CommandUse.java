package cc.dreamcode.template.commands;

import cc.dreamcode.template.PluginMain;
import cc.dreamcode.template.exception.PluginValidationException;
import cc.dreamcode.template.features.notice.NoticeService;
import cc.dreamcode.template.features.validation.ValidationService;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandUse extends Command implements PluginIdentifiableCommand, NoticeService, ValidationService {

    public CommandUse(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return PluginMain.getPluginMain();
    }

    public abstract void run(@NonNull CommandSender sender, @NonNull String[] args);

    public abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, @NonNull String[] arguments) {
        try {
            run(sender, arguments);
        }
        catch (PluginValidationException e) {
            if (e.getReplaceMap().isEmpty()) {
                this.send(e.getNotice(), sender);
            }
            else {
                this.send(e.getNotice(), sender, e.getReplaceMap());
            }
        }
        return true;
    }

    public @NonNull List<String> tabComplete(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        List<String> completions = tab((Player) sender, args);
        if (completions == null) {
            return new ArrayList<>();
        }
        return completions;
    }

}
