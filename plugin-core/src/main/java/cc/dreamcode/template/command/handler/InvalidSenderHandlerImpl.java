package cc.dreamcode.template.command.handler;

import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.handler.InvalidSenderHandler;
import cc.dreamcode.template.config.MessageConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InvalidSenderHandlerImpl implements InvalidSenderHandler {

    private final MessageConfig messageConfig;

    @Override
    public void handle(@NonNull DreamSender<?> dreamSender, @NonNull List<DreamSender.Type> requireType) {
        final BukkitSender bukkitSender = (BukkitSender) dreamSender;

        if (requireType.contains(DreamSender.Type.CONSOLE)) {
            this.messageConfig.notConsole.send(bukkitSender.getHandler());
            return;
        }

        if (requireType.contains(DreamSender.Type.CLIENT)) {
            this.messageConfig.notPlayer.send(bukkitSender.getHandler());
        }
    }
}
