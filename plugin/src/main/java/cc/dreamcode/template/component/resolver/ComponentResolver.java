package cc.dreamcode.template.component.resolver;

import cc.dreamcode.template.PluginMain;
import eu.okaeri.injector.Injector;

public interface ComponentResolver<T> {

    void resolve(PluginMain pluginMain, Injector injector, T resolve);

}
