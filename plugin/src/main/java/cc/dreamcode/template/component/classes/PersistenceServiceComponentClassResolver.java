package cc.dreamcode.template.component.classes;

import cc.dreamcode.template.component.resolvers.ComponentClassResolver;
import cc.dreamcode.template.persistence.PersistenceService;
import eu.okaeri.injector.Injector;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class PersistenceServiceComponentClassResolver extends ComponentClassResolver<Class<PersistenceService>> {

    @Override
    public boolean isAssignableFrom(@NonNull Class<PersistenceService> serviceClass) {
        return PersistenceService.class.isAssignableFrom(serviceClass);
    }

    @Override
    public String getComponentName() {
        return "service";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<PersistenceService> serviceClass) {
        return new HashMap<>();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<PersistenceService> serviceClass) {
        final PersistenceService service = injector.createInstance(serviceClass);
        service.loadAllToMemory();

        return service;
    }
}
