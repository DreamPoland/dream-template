package cc.dreamcode.template.features.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

@Getter
public class MenuBase {

    private final int rows;
    private final Inventory inventory;
    private final MenuHolder menuHolder;

    public MenuBase(String name, int rows) {
        this.rows = rows;
        this.menuHolder = new MenuHolder(this);
        this.inventory = Bukkit.createInventory(this.menuHolder, rows > 6 ? 6 * 9 : rows * 9, name);
    }

    public void setItem(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    public void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> event) {
        this.menuHolder.setActionOnSlot(slot, event);
        this.inventory.setItem(slot, item);
    }

    public void addItem(ItemStack item) {
        for (int slot = 0; slot < this.rows * 9; slot++) {
            if (this.inventory.getItem(slot) == null) {
                this.inventory.setItem(slot, item);
                return;
            }
        }
    }

    public void addItem(ItemStack item, Consumer<InventoryClickEvent> event) {
        for (int slot = 0; slot < this.rows * 9 - 1; slot++) {
            if (this.inventory.getItem(slot) == null) {
                this.menuHolder.setActionOnSlot(slot, event);
                this.inventory.setItem(slot, item);
                return;
            }
        }
    }
}
