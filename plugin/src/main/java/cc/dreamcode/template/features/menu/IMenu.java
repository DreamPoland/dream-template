package cc.dreamcode.template.features.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import java.util.Map;
import java.util.function.Consumer;

public interface IMenu extends InventoryHolder {

    Map<Integer, Consumer<InventoryClickEvent>> getActions();

    void open(HumanEntity entity);

}
