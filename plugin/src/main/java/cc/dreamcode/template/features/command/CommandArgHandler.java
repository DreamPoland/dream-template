package cc.dreamcode.template.features.command;

import cc.dreamcode.template.features.notice.NoticeService;
import cc.dreamcode.template.features.validation.ValidationService;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

public interface CommandArgHandler extends NoticeService, ValidationService {

    void handle(@NonNull CommandSender sender, @NonNull String[] args);

}
