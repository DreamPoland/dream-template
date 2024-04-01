package cc.dreamcode.template.command.handler;

import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.handler.InvalidInputHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InvalidInputHandlerImpl implements InvalidInputHandler {

    private final MessageConfig messageConfig;

    @Override
    public void handle(@NonNull DreamSender<?> dreamSender, @NonNull Class<?> requiringClass, @NonNull String input) {
        final BukkitSender bukkitSender = (BukkitSender) dreamSender;

        if (requiringClass.isAssignableFrom(Player.class)) {
            this.messageConfig.playerNotFound.send(bukkitSender.getHandler());
            return;
        }

        if (requiringClass.isAssignableFrom(World.class)) {
            this.messageConfig.worldNotFound.send(bukkitSender.getHandler());
            return;
        }

        this.messageConfig.invalidFormat.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                .put("input", input)
                .build());
    }
}
