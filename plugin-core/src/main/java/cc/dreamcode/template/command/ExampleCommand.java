package cc.dreamcode.template.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Completion;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.OptArg;
import org.bukkit.entity.Player;

@Command(name = "example")
public class ExampleCommand implements CommandBase {

    @Executor(description = "Example command.")
    @Completion(arg = "arg2", value = {"1", "2", "3"})
    void example(Player player, @Arg Player arg, @Arg int arg2, @OptArg String arg3) {
        player.sendMessage("hello, " + arg.getName());
        player.sendMessage("hello 2, " + arg2);
        player.sendMessage("hello 3, " + arg3);
    }
}
