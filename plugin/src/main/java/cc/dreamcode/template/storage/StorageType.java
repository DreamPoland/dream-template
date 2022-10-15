package cc.dreamcode.template.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StorageType {
    FLAT("FLAT"),
    MYSQL("MySQL"),
    H2("H2"),
    REDIS("Redis"),
    MONGODB("MongoDB");

    private final String name;
}
