package cc.dreamcode.template.command.handler;

import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.handler.InvalidPermissionHandler;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InvalidPermissionHandlerImpl implements InvalidPermissionHandler {

    private final MessageConfig messageConfig;

    @Override
    public void handle(@NonNull DreamSender<?> dreamSender, @NonNull String permission) {
        final BukkitSender bukkitSender = (BukkitSender) dreamSender;

        this.messageConfig.noPermission.send(bukkitSender.getHandler(), new MapBuilder<String, Object>()
                .put("permission", permission)
                .build());
    }
}
