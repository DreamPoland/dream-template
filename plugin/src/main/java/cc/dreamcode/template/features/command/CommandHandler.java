package cc.dreamcode.template.features.command;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.exception.PluginValidatorException;
import cc.dreamcode.template.features.command.annotations.RequiredPermission;
import cc.dreamcode.template.features.command.annotations.RequiredPlayer;
import cc.dreamcode.template.features.menu.MenuBaseBuilder;
import cc.dreamcode.template.features.notice.NoticeSender;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler extends Command implements PluginIdentifiableCommand, NoticeSender, CommandValidator {

    private @Inject MessageConfig messageConfig;

    public CommandHandler(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return TemplatePlugin.getTemplatePlugin();
    }

    public abstract void handle(@NonNull CommandSender sender, @NonNull String[] args);

    public abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, @NonNull String[] arguments) {
        try {
            RequiredPermission requiredPermission = getClass().getAnnotation(RequiredPermission.class);
            if (requiredPermission != null) {
                whenNot(sender.hasPermission(requiredPermission.permission()), this.messageConfig.noPermission);
            }

            RequiredPlayer requiredPlayer = getClass().getAnnotation(RequiredPlayer.class);
            if (requiredPlayer != null) {
                whenNot(sender instanceof Player, this.messageConfig.noPlayer);
            }

            handle(sender, arguments);
        }
        catch (PluginValidatorException e) {
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

    public CommandArgHandler getArgument(@NonNull CommandSender sender, @NonNull Class<? extends CommandArgHandler> commandArgHandlerClass) {
        RequiredPermission requiredPermission = commandArgHandlerClass.getAnnotation(RequiredPermission.class);
        if (requiredPermission != null) {
            whenNot(sender.hasPermission(requiredPermission.permission()), this.messageConfig.noPermission);
        }

        RequiredPlayer requiredPlayer = commandArgHandlerClass.getAnnotation(RequiredPlayer.class);
        if (requiredPlayer != null) {
            whenNot(sender instanceof Player, this.messageConfig.noPlayer);
        }

        return TemplatePlugin.getTemplatePlugin().getInjector().createInstance(commandArgHandlerClass);
    }

    public MenuBaseBuilder getMenu(@NonNull Class<? extends MenuBaseBuilder> menuBaseBuilder) {
        return TemplatePlugin.getTemplatePlugin().getInjector().createInstance(menuBaseBuilder);
    }

}
