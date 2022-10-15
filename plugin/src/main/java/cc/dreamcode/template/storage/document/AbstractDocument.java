package cc.dreamcode.template.storage.document;

import cc.dreamcode.template.TemplatePlugin;
import cc.dreamcode.template.scheduler.SchedulerService;
import eu.okaeri.persistence.document.Document;

import java.util.concurrent.TimeUnit;

/**
 * Abstract class for asynchronous save method in document.
 */
public abstract class AbstractDocument extends Document {
    public void saveAsync() {
        TemplatePlugin.getTemplatePlugin().getInject(SchedulerService.class).ifPresent(schedulerService ->
                schedulerService.asyncLater(this::save, 0L, TimeUnit.MILLISECONDS));
    }
}
