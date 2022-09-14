package cc.dreamcode.template.component.resolvers;

import cc.dreamcode.template.component.ComponentResolver;
import cc.dreamcode.template.features.persistence.service.Service;
import eu.okaeri.injector.Injector;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ServiceComponentResolver extends ComponentResolver<Class<Service>> {

    @Override
    public boolean isAssignableFrom(@NonNull Class<Service> serviceClass) {
        return Service.class.isAssignableFrom(serviceClass);
    }

    @Override
    public String getComponentName() {
        return "service";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<Service> serviceClass) {
        return new HashMap<>();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<Service> serviceClass) {
        final Service service = injector.createInstance(serviceClass);
        service.loadAllToMemory();

        return service;
    }
}
