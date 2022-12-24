package cc.dreamcode.template.controller;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.discord4j.component.listener.EventHandler;
import cc.dreamcode.platform.discord4j.component.listener.Listener;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import eu.okaeri.injector.annotation.Inject;

/**
 * Example usage of events.
 * Register controller in platform component system.
 */
public class ExampleUserController implements Listener {

    private @Inject DreamLogger dreamLogger;

    @EventHandler
    public void onReadyEvent(ReadyEvent e) {
        this.dreamLogger.info("Logged as ok " + e.getSelf().getUsername());
    }

}
