package cc.dreamcode.template.component.objects;

import cc.dreamcode.template.component.resolvers.ComponentObjectResolver;
import eu.okaeri.injector.Injector;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class GenericComponentObjectResolver extends ComponentObjectResolver<Object> {
    @Override
    public boolean isAssignableFrom(@NonNull Class<Object> t) {
        return false;
    }

    @Override
    public String getComponentName() {
        return "generic";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Object o) {
        return new HashMap<>();
    }
}
