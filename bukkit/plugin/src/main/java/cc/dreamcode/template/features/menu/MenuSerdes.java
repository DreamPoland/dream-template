package cc.dreamcode.template.features.menu;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class MenuSerdes implements ObjectSerializer<MenuBuilder> {
    @Override
    public boolean supports(@NonNull Class<? super MenuBuilder> type) {
        return MenuBuilder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull MenuBuilder object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("rows", object.getRows());
        data.addAsMap("items", object.getItems(), Integer.class, ItemStack.class);
    }

    @Override
    public MenuBuilder deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new MenuBuilder(data.get("name", String.class),
                data.get("rows", Integer.class),
                data.getAsMap("items", Integer.class, ItemStack.class));
    }
}
