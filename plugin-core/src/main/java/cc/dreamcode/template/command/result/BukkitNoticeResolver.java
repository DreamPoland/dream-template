package cc.dreamcode.template.command.result;

import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.bukkit.BukkitSender;
import cc.dreamcode.command.result.ResultResolver;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import lombok.NonNull;

public class BukkitNoticeResolver implements ResultResolver {
    @Override
    public boolean isAssignableFrom(@NonNull Class<?> type) {
        return BukkitNotice.class.isAssignableFrom(type);
    }

    @Override
    public void resolveResult(@NonNull DreamSender<?> sender, @NonNull Class<?> type, @NonNull Object object) {
        final BukkitSender bukkitSender = (BukkitSender) sender;
        final BukkitNotice BukkitNotice = (BukkitNotice) object;

        BukkitNotice.send(bukkitSender.getHandler());
    }
}
