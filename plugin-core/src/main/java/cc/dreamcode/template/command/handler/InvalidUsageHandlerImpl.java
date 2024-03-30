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
        if (commandMeta.getCommandPaths().isEmpty()) {
            this.messageConfig.pathNotFound.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                    .put("label", "/" + commandMeta.getCommandContext().getName())
                    .put("description", commandMeta.getCommandContext().getDescription())
                    .build());
            return;
        }

        this.messageConfig.usage.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                .put("label", "/" + commandMeta.getCommandContext().getName())
                .put("description", commandMeta.getCommandContext().getDescription())
                .build());

        for (CommandPathMeta commandPath : commandMeta.getCommandPaths()) {
            this.messageConfig.usagePath.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                    .put("usage", commandPath.getUsage())
                    .put("description", commandPath.getDescription())
                    .build());
        }
    }
}
