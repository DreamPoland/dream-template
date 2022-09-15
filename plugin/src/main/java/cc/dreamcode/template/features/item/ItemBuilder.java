package cc.dreamcode.template.features.item;

import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@Setter
public class ItemBuilder {
    private ItemStack itemStack = new ItemStack(Material.AIR);

    public ItemBuilder(Material material) {
        if (material == null) {
            this.setItemStack(new ItemStack(Material.DIRT));
            return;
        }

        this.setItemStack(new ItemStack(material));
    }

    public ItemBuilder(Material material, int amount) {
        if (material == null) {
            this.setItemStack(new ItemStack(Material.DIRT, amount));
            return;
        }

        this.setItemStack(new ItemStack(material, amount));
    }

    public ItemBuilder(ItemStack itemStack) {
        if (itemStack == null) {
            this.setItemStack(new ItemStack(Material.DIRT));
            return;
        }

        this.setItemStack(itemStack);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(name);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean visible) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.addEnchant(enchantment, level, visible);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        return this.addEnchant(enchantment, level, true);
    }

    public ItemStack toItemStack() {
        return this.itemStack;
    }
}
