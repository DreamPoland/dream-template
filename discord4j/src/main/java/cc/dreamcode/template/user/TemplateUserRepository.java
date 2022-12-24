package cc.dreamcode.template.user;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.User;
import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@DocumentCollection(path = "user", keyLength = 36)
public interface TemplateUserRepository extends DocumentRepository<Snowflake, TemplateUser> {

    default CompletableFuture<TemplateUser> findOrCreate(@NonNull Snowflake snowflake, String userName) {
        return CompletableFuture.supplyAsync(() -> {
            TemplateUser templateUser = this.findOrCreateByPath(snowflake);
            if (userName != null) {
                templateUser.setUserName(userName);
            }
            return templateUser;
        });
    }

    default CompletableFuture<TemplateUser> findOrCreateBySnowflake(@NonNull Snowflake snowflake) {
        return this.findOrCreate(snowflake, null);
    }

    default CompletableFuture<TemplateUser> findOrCreateByUser(@NonNull User user) {
        return this.findOrCreate(user.getId(), user.getUsername());
    }

    default CompletableFuture<Optional<TemplateUser>> findByName(@NonNull String name, boolean ignoreCase) {
        return CompletableFuture.supplyAsync(() -> this.streamAll()
                .filter(templateUser -> ignoreCase
                        ? templateUser.getUserName().equalsIgnoreCase(name)
                        : templateUser.getUserName().equals(name))
                .findFirst());
    }

}
