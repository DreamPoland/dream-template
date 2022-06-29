package cc.dreamcode.template.features.menu;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MenuHolder implements IMenu {

    private final MenuBase menuBase;
    private final Map<Integer, Consumer<InventoryClickEvent>> actions = new ConcurrentHashMap<>();

    @Override
    public Map<Integer, Consumer<InventoryClickEvent>> getActions() {
        return this.menuBase.getMenuHolder().getActions();
    }

    @Override
    public void open(HumanEntity entity) {
        entity.openInventory(this.menuBase.getInventory());
    }

    @Override
    public @Nonnull Inventory getInventory() {
        return this.menuBase.getInventory();
    }

    public void setActionOnSlot(Integer slot, Consumer<InventoryClickEvent> consumer) {
        actions.put(slot, consumer != null ? consumer : event -> {});
    }

    public void handleClick(InventoryClickEvent event) {
        actions.getOrDefault(event.getRawSlot(), e -> e.setCancelled(true)).accept(event);
    }

}
