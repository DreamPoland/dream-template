package cc.dreamcode.template.features.command;

import cc.dreamcode.template.features.notice.NoticeSender;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

public interface CommandArgHandler extends NoticeSender, CommandValidator {

    void handle(@NonNull CommandSender sender, @NonNull String[] args);

}
