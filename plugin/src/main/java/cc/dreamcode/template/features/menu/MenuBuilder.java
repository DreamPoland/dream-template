package cc.dreamcode.template.features.menu;

import cc.dreamcode.template.features.item.ItemReplacer;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class MenuBuilder {

    private final String name;
    private final int rows;
    private final Map<Integer, ItemStack> items;

    public MenuBase build() {
        return new MenuBase(ChatColor.translateAlternateColorCodes('&', this.name), this.rows);
    }

    public MenuBase build(Map<String, Object> replaceMap) {
        return new MenuBase(PlaceholderContext.of(
                        CompiledMessage.of(
                                ChatColor.translateAlternateColorCodes('&', this.name)
                        ))
                .with(replaceMap)
                .apply(), this.rows);
    }

    public MenuBase buildWithItem() {
        MenuBase menuBase = new MenuBase(ChatColor.translateAlternateColorCodes('&', this.name), this.rows);

        this.items.forEach(((integer, itemStack) ->
                menuBase.setItem(integer, new ItemReplacer(itemStack).fixColors())));

        return menuBase;
    }

    public MenuBase buildWithItem(Map<String, Object> replaceMap) {
        MenuBase menuBase = new MenuBase(PlaceholderContext.of(
                        CompiledMessage.of(
                                ChatColor.translateAlternateColorCodes('&', this.name)
                        ))
                .with(replaceMap)
                .apply(), this.rows);

        this.items.forEach(((integer, itemStack) ->
                menuBase.setItem(integer, new ItemReplacer(itemStack).fixColors())));

        return menuBase;
    }

}
