package cc.dreamcode.template.persistence.document;

import eu.okaeri.persistence.document.Document;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract class for asynchronous save method in document.
 */
public abstract class AbstractDocument extends Document {
    public void saveASync() {
        CompletableFuture.runAsync(this::save);
    }
}
