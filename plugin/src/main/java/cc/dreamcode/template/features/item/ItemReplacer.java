package cc.dreamcode.template.features.item;

import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ItemReplacer { // TODO: improve it

    private final ItemStack is;

    public static ItemReplacer of(@NonNull ItemStack itemStack) {
        return new ItemReplacer(itemStack);
    }

    public ItemStack fixColors() {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if (im.hasDisplayName()) {
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', im.getDisplayName()));
        }

        if (im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore())
                    .stream()
                    .map(text -> ChatColor.translateAlternateColorCodes('&', text))
                    .collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore())
                    .stream()
                    .map(text -> ChatColor.translateAlternateColorCodes('&', text))
                    .collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(List<String> lore) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if (im.hasDisplayName()) {
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', im.getDisplayName()));
        }

        im.setLore(Objects.requireNonNull(lore)
                .stream()
                .map(text -> ChatColor.translateAlternateColorCodes('&', text))
                .collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name, List<String> lore) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        im.setLore(Objects.requireNonNull(lore)
                .stream()
                .map(text -> ChatColor.translateAlternateColorCodes('&', text))
                .collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack fixColors(Map<String, Object> replaceMap) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if (im.hasDisplayName()) {
            im.setDisplayName(PlaceholderContext.of(
                    CompiledMessage.of(im.getDisplayName()))
                    .with(replaceMap)
                    .apply());
        }

        if (im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore())
                    .stream()
                    .map(text -> PlaceholderContext.of(
                                    CompiledMessage.of(text))
                            .with(replaceMap)
                            .apply())
                    .collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name, Map<String, Object> replaceMap) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(PlaceholderContext.of(
                        CompiledMessage.of(name))
                .with(replaceMap)
                .apply());

        if (im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore())
                    .stream()
                    .map(text -> PlaceholderContext.of(
                                    CompiledMessage.of(text))
                            .with(replaceMap)
                            .apply())
                    .collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(List<String> lore, Map<String, Object> replaceMap) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if (im.hasDisplayName()) {
            im.setDisplayName(PlaceholderContext.of(
                            CompiledMessage.of(im.getDisplayName()))
                    .with(replaceMap)
                    .apply());
        }

        im.setLore(Objects.requireNonNull(lore)
                .stream()
                .map(text -> PlaceholderContext.of(
                                CompiledMessage.of(text))
                        .with(replaceMap)
                        .apply())
                .collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name, List<String> lore, Map<String, Object> replaceMap) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(PlaceholderContext.of(
                        CompiledMessage.of(name))
                .with(replaceMap)
                .apply());

        im.setLore(Objects.requireNonNull(lore)
                .stream()
                .map(text -> PlaceholderContext.of(
                                CompiledMessage.of(text))
                        .with(replaceMap)
                        .apply())
                .collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

}
