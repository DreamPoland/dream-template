package cc.dreamcode.template.command;

import cc.dreamcode.command.DreamCommandExecutor;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Path;
import cc.dreamcode.command.bukkit.sender.BukkitSender;

@Command(label = "example", description = "Example command.")
public class ExampleCommand extends DreamCommandExecutor {

    @Path(name = "test")
    void exampleResponse(BukkitSender sender) {
        sender.sendMessage("Example response.");
    }

    @Path(name = "test2")
    void exampleResponse2(BukkitSender sender) {
        sender.sendMessage("Example response.");
    }

    @Path(name = "test3")
    void exampleResponse3(BukkitSender sender) {
        sender.sendMessage("Example response.");
    }
}
