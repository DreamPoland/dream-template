package cc.dreamcode.template.command;

import cc.dreamcode.platform.discord4j.component.command.DiscordCommand;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import lombok.NonNull;

import java.util.List;

/**
 * Example usage of user repository.
 * Register command in platform component system.
 */
public class ExampleTestCommand implements DiscordCommand {
    @Override
    public ApplicationCommandRequest commandRequest() {
        return ApplicationCommandRequest.builder()
                .name("test")
                .description("Test command.")
                .build();
    }

    @Override
    public void content(@NonNull ChatInputInteractionEvent event) {
        event.reply("test");
    }

    @Override
    public List<ApplicationCommandOptionChoiceData> autoComplete(@NonNull ChatInputAutoCompleteEvent event) {
        return null;
    }
}
