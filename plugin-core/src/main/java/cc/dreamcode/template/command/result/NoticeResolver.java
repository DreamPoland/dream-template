package cc.dreamcode.template.command.result;

import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.result.ResultResolver;
import cc.dreamcode.notice.minecraft.adventure.bukkit.AdventureBukkitNotice;
import lombok.NonNull;

public class NoticeResolver implements ResultResolver {
    @Override
    public boolean isAssignableFrom(@NonNull Class<?> type) {
        return AdventureBukkitNotice.class.isAssignableFrom(type);
    }

    @Override
    public void resolveResult(@NonNull DreamSender<?> sender, @NonNull Class<?> type, @NonNull Object object) {
        final BukkitSender bukkitSender = (BukkitSender) sender;
        final AdventureBukkitNotice adventureBukkitNotice = (AdventureBukkitNotice) object;

        adventureBukkitNotice.send(bukkitSender.getHandler());
    }
}
