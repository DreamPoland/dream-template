package cc.dreamcode.template.command.handler;

import cc.dreamcode.command.CommandInput;
import cc.dreamcode.command.CommandMeta;
import cc.dreamcode.command.CommandPathMeta;
import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.handler.InvalidUsageHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InvalidUsageHandlerImpl implements InvalidUsageHandler {

    private final MessageConfig messageConfig;

    @Override
    public void handle(@NonNull DreamSender<?> dreamSender, @NonNull Optional<CommandMeta> optionalCommandMeta, @NonNull CommandInput commandInput) {
        final BukkitSender bukkitSender = (BukkitSender) dreamSender;

        if (!optionalCommandMeta.isPresent()) {
            this.messageConfig.usageNotFound.send(bukkitSender.getHandler());
            return;
        }

        final CommandMeta commandMeta = optionalCommandMeta.get();
        final List<CommandPathMeta> commandPathMetas = commandMeta.getFilteredCommandPaths(dreamSender);
        if (commandPathMetas.isEmpty()) {
            this.messageConfig.pathNotFound.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                    .put("label", "/" + commandMeta.getCommandEntry().getName())
                    .put("description", commandMeta.getCommandEntry().getDescription())
                    .build());
            return;
        }

        this.messageConfig.usage.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                .put("label", "/" + commandMeta.getCommandEntry().getName())
                .put("description", commandMeta.getCommandEntry().getDescription())
                .build());

        for (CommandPathMeta commandPathMeta : commandPathMetas) {
            this.messageConfig.usagePath.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                    .put("usage", commandPathMeta.getUsage())
                    .put("description", commandPathMeta.getDescription())
                    .build());
        }
    }
}
