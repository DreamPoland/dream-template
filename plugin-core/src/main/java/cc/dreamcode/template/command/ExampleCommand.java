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

import java.time.Duration;

@Command(name = "example")
public class ExampleCommand implements CommandBase {

    @Executor(description = "Przykladowa komenda.")
    @Sender(type = DreamSender.Type.CLIENT)
    @Completion(arg = "bro", value = "@allplayers")
    @Completion(arg = "bro2", value = {"1", "2", "3"})
    public void example(Player player, @Arg(name = "bro") Player bro, @Arg(name = "bro2") Duration bro2, @OptArg(name = "bro3") String bro3) {
        player.sendMessage("hello, " + bro.getName());
        player.sendMessage("hello 2, " + bro2);
        player.sendMessage("hello 3, " + bro3);
    }
}
