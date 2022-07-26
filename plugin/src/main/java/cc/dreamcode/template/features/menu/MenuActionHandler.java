package cc.dreamcode.template.features.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuActionHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final Inventory inventory = e.getInventory();
        if (!inventory.getType().equals(InventoryType.CHEST)) {
            return;
        }

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof MenuHolder) {
            e.setCancelled(true);
            MenuHolder menuHolder = (MenuHolder) inventoryHolder;
            menuHolder.handleClick(e);
        }
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent e) {
        final Inventory inventory = e.getInventory();
        if (!inventory.getType().equals(InventoryType.CHEST)) {
            return;
        }

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof MenuHolder) {
            e.setCancelled(true);
        }
    }
}
