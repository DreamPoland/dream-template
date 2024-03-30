package cc.dreamcode.template.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Completion;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.OptArg;
import cc.dreamcode.command.bukkit.BukkitSender;

import java.time.Duration;

@Command(name = "example")
public class ExampleCommand implements CommandBase {

    @Executor(description = "Przykladowa komenda.")
    @Completion(arg = "bro", value = "@allplayers")
    @Completion(arg = "bro2", value = {"1", "2", "3"})
    public void example(BukkitSender bukkitSender, @Arg(name = "bro") String bro, @Arg(name = "bro2") Duration bro2, @OptArg(name = "bro3") String bro3) {
        bukkitSender.getHandler().sendMessage("hello, " + bro);
        bukkitSender.getHandler().sendMessage("hello 2, " + bro2);
        bukkitSender.getHandler().sendMessage("hello 3, " + bro3);
    }
}
