package cc.dreamcode.template.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.DreamSender;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Completion;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.OptArg;
import cc.dreamcode.command.annotation.Sender;
import org.bukkit.entity.Player;

@Command(name = "example")
public class ExampleCommand implements CommandBase {

    @Executor(description = "Przykladowa komenda.")
    @Sender(type = DreamSender.Type.CLIENT)
    @Completion(arg = "arg", value = "@allplayers")
    @Completion(arg = "arg2", value = {"1", "2", "3"})
    void example(
            Player player,
            @Arg(name = "arg") Player arg,
            @Arg(name = "arg2") int arg2,
            @OptArg(name = "arg3") String arg3
    ) {
        player.sendMessage("hello, " + arg.getName());
        player.sendMessage("hello 2, " + arg2);
        player.sendMessage("hello 3, " + arg3);
    }
}
