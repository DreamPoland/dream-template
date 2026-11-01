# Dream-Template

[![Build](https://github.com/DreamPoland/dream-template/actions/workflows/gradle.yml/badge.svg)](https://github.com/DreamPoland/dream-template/actions/workflows/gradle.yml)
[![Java](https://img.shields.io/badge/java-21%20%7C%2025-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENCE)

Modern, multi-version Spigot/Paper Minecraft plugin template powered by **Dream-Platform**, with **Okaeri Framework**, **Paperweight UserDev**, and modular **NMS** architecture.

---

## Features

- **Dream-Platform**: Component-based application lifecycle and dependency injection (`@Inject`, `registerInjectable`, `registerComponent`).
- **Modular Multi-Version NMS**:
  - Independent version modules from **1.8.8** to **1.21.x+** (including preparation for 26.x).
  - Modern **Paperweight UserDev** toolchain integration for versions 1.19+.
  - Dynamic runtime `VersionAccessor` resolution via reflection.
- **Okaeri Configs**:
  - Declarative YAML configuration with comments (`@Comment`, `@Header`).
  - Pre-registered Serdes for `ItemStack` (Bukkit & Base64), `ItemMeta`, `Instant`, `BukkitNotice`, and `MenuBuilder`.
  - Live configuration reloading (`/example reload`).
- **Okaeri Persistence**:
  - Document-store repository pattern (`ProfileRepository` / `Profile`).
  - Pluggable storage backends configured via `config.yml` (Flat, H2, MySQL, MariaDB, Mongo, Redis).
- **Dream-Command**:
  - Annotation-driven command routing with `@Command`, `@Executor`, `@Permission`, and `@Async`.
  - Custom command exception handlers (invalid usage, missing permission, sender mismatch, invalid argument format).
- **Dream-Notice**:
  - Multi-channel notification dispatching (`CHAT`, `ACTION_BAR`, `TITLE`, `SUBTITLE`, `TITLE_SUBTITLE`).
  - Kyori Adventure & RGB / HEX color support with placeholder formatting.
- **Dream-Menu**: Interactive GUI menu building with declarative serialization.
- **BukkitTasker**: Clean and safe async/sync task scheduling.
- **Automated Shading & Relocations**: Pre-configured Gradle ShadowJar with comprehensive package relocation preventing classpath collisions on servers.

---

## Requirements

- **Java 21** or newer (Java 25 recommended for modern Paperweight userdev toolchains).
- **Minecraft 1.8.8 – 1.21.x+** (Spigot, Paper, or forks).
- **Gradle 9.x+** (Gradle wrapper included).

---

## Project Structure

```text
dream-template/
├── .github/workflows/gradle.yml  # GitHub Actions CI/CD release workflow
├── plugin-core/
│   ├── nms/                      # Multi-version NMS abstraction
│   │   ├── api/                  # Shared API (VersionAccessor, VersionProvider)
│   │   ├── v1_8_R3/              # Minecraft 1.8.8 implementation
│   │   ├── v1_12_R1/             # Minecraft 1.12.2 implementation
│   │   ├── v1_16_R3/ ... v1_20_R5/
│   │   ├── v1_21_R1/ ... v1_21_R7/
│   │   └── v1_26_R1/
│   └── src/main/
│       ├── java/cc/dreamcode/template/
│       │   ├── command/          # Commands & exception handlers
│       │   ├── config/           # Okaeri Config definitions
│       │   ├── profile/          # Persistence documents & repositories
│       │   └── TemplatePlugin.java # Main platform entrypoint
│       └── resources/plugin.yml  # Spigot/Paper plugin descriptor
├── build.gradle.kts              # Root build configuration
└── settings.gradle.kts           # Gradle project & toolchain settings
```

---

## Getting Started

### 1. Use Template

Click **"Use this template"** on GitHub or clone the repository:

```bash
git clone https://github.com/DreamPoland/dream-template.git my-plugin
cd my-plugin
```

### 2. Configure Project

Update the project coordinates and metadata:

- **`settings.gradle.kts`**:
  ```kotlin
  rootProject.name = "my-plugin"
  ```

- **`build.gradle.kts`**:
  ```kotlin
  allprojects {
      group = "com.example.myplugin"
      version = "1.0.0"
  }
  ```

- **`plugin-core/build.gradle.kts`**:
  Update the final JAR output name and dependency relocation targets:
  ```kotlin
  tasks.withType<ShadowJar> {
      archiveFileName.set("MyPlugin-${project.version}.jar")
      relocate("cc.dreamcode.platform", "com.example.myplugin.libs.cc.dreamcode.platform")
      // ...
  }
  ```

- **`plugin-core/src/main/resources/plugin.yml`**:
  ```yaml
  main: "com.example.myplugin.MyPlugin"
  version: "1.0.0"
  name: "MyPlugin"
  description: "My custom Minecraft plugin."
  author: "YourName"
  api-version: 1.13
  ```

### 3. Refactor Package Names

Refactor `cc.dreamcode.template` to your target package (e.g. `com.example.myplugin`) in your IDE (`Shift + F6` in IntelliJ IDEA).

---

## Building

To compile all NMS modules, shade dependencies, and produce the final distribution JAR:

#### Linux / macOS:
```bash
./gradlew clean plugin-core:shadowJar
```

#### Windows:
```cmd
.\gradlew.bat clean plugin-core:shadowJar
```

The compiled plugin will be generated in:
```text
plugin-core/build/libs/Dream-Template-1.0-InDEV.jar
```

---

## Usage

### 1. Platform & Lifecycle

The main class extends `DreamBukkitPlatform` and implements `DreamBukkitConfig` and `DreamPersistence`:

```java
public final class TemplatePlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static TemplatePlugin instance;

    @Override
    public void load(@NonNull ComponentService componentService) {
        instance = this;
        AdventureUtil.setRgbSupport(true);
        StringColorUtil.setColorProcessor(new AdventureProcessor());
    }

    @Override
    public void enable(@NonNull ComponentService componentService) {
        // Register injectable services
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this));
        this.registerInjectable(VersionProvider.getVersionAccessor());

        // Register components & configs
        componentService.registerResolver(ConfigurationResolver.class);
        componentService.registerComponent(MessageConfig.class);
        componentService.registerComponent(PluginConfig.class, pluginConfig -> {
            this.registerInjectable(pluginConfig.storageConfig);
            componentService.registerResolver(DocumentPersistenceResolver.class);
            componentService.registerComponent(DocumentPersistence.class);
            componentService.registerResolver(DocumentRepositoryResolver.class);
        });

        componentService.registerComponent(ProfileRepository.class);
        componentService.registerComponent(ExampleCommand.class);
    }

    @Override
    public void disable() {
        // Cleanup logic when plugin disables
    }
}
```

---

### 2. Creating Commands (Dream-Command)

Commands support dependency injection, asynchronous execution, sub-paths, and permission validation:

```java
@Command(name = "test", aliases = {"testing"})
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TestCommand implements CommandBase {

    private final MessageConfig messageConfig;

    @Executor(description = "Default command execution")
    public BukkitNotice execute(Player player) {
        return BukkitNotice.chat("&aHello, &e{player}&a!")
                .with("player", player.getName());
    }

    @Async
    @Permission("myplugin.admin")
    @Executor(path = "reload", description = "Reload plugin configuration asynchronously")
    public BukkitNotice reload() {
        this.messageConfig.load();
        return BukkitNotice.chat("&aConfiguration reloaded!");
    }
}
```

Register the command in `enable`:
```java
componentService.registerComponent(TestCommand.class);
```

---

### 3. Declarative Configuration (Okaeri-Configs)

Define structured YAML configs with comments:

```java
@Configuration(child = "config.yml")
@Header("## MyPlugin (Main-Config) ##")
public class PluginConfig extends OkaeriConfig {

    @Comment("Enable debug messages in console:")
    @CustomKey("debug")
    public boolean debug = false;

    @Comment("Database connection credentials:")
    @CustomKey("storage-config")
    public StorageConfig storageConfig = new StorageConfig("myplugin");
}
```

---

### 4. Player Profiles & Persistence (Okaeri-Persistence)

Define document entities:

```java
@Data
@EqualsAndHashCode(callSuper = false)
public class Profile extends Document {

    @CustomKey("name")
    private String name;

    @CustomKey("coins")
    private int coins = 0;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }
}
```

Define the repository interface:

```java
@DocumentCollection(path = "profiles", keyLength = 36)
public interface ProfileRepository extends DocumentRepository<UUID, Profile> {

    default Profile findOrCreate(@NonNull UUID uuid, String profileName) {
        Profile profile = this.findOrCreateByPath(uuid);
        if (profileName != null) {
            profile.setName(profileName);
        }
        return profile;
    }
}
```

---

### 5. Sending Notices (Dream-Notice)

Send styled messages, action bars, or titles across versions:

```java
// Chat notice with placeholder formatting
BukkitNotice.chat("&7Welcome &a{player}&7 to the server!")
    .with("player", player.getName())
    .send(player);

// ActionBar notice
BukkitNotice.actionbar("&aProgress saved successfully!").send(player);

// Title & Subtitle notice
BukkitNotice.title("&6VICTORY!", "&7You won the match!").send(player);
```

---

### 6. Multi-Version NMS Abstraction

1. Add your version-specific methods to `VersionAccessor`:
   ```java
   public interface VersionAccessor {
       void sendPacket(Player player, Object packet);
   }
   ```
2. Implement the methods in each submodule (e.g. `plugin-core:nms:v1_8_R3`, `v1_21_R3`, etc.).
3. Inject and use `VersionAccessor` anywhere in your code:
   ```java
   @Inject
   private VersionAccessor versionAccessor;
   ```

---

## Integrated Libraries

| Library | Author | Description |
| :--- | :--- | :--- |
| [**Dream-Platform**](https://github.com/DreamPoland/dream-platform) | [DreamCode](https://github.com/DreamPoland) | Core application lifecycle and component injection |
| [**Dream-Command**](https://github.com/DreamPoland/dream-command) | [DreamCode](https://github.com/DreamPoland) | Annotation-driven command routing |
| [**Dream-Notice**](https://github.com/DreamPoland/dream-notice) | [DreamCode](https://github.com/DreamPoland) | Multi-channel notification and Adventure text engine |
| [**Dream-Menu**](https://github.com/DreamPoland/dream-menu) | [DreamCode](https://github.com/DreamPoland) | Declarative inventory GUI builders and serializers |
| [**Dream-Utilities**](https://github.com/DreamPoland/dream-utilities) | [DreamCode](https://github.com/DreamPoland) | Adventure color processors, time formatters, builders |
| [**Okaeri-Configs**](https://github.com/OkaeriPoland/okaeri-configs) | [Okaeri](https://github.com/OkaeriPoland) | Declarative YAML configurations with Serdes |
| [**Okaeri-Persistence**](https://github.com/OkaeriPoland/okaeri-persistence) | [Okaeri](https://github.com/OkaeriPoland) | Multi-backend document database abstraction |
| [**Okaeri-Injector**](https://github.com/OkaeriPoland/okaeri-injector) | [Okaeri](https://github.com/OkaeriPoland) | Dependency injection framework |
| [**Okaeri-Tasker**](https://github.com/OkaeriPoland/okaeri-tasker) | [Okaeri](https://github.com/OkaeriPoland) | Asynchronous and synchronous task management |
| [**XSeries**](https://github.com/CryptoMorin/XSeries) | [CryptoMorin](https://github.com/CryptoMorin) | Multi-version Bukkit materials and sounds |
| [**Lombok**](https://projectlombok.org/) | [ProjectLombok](https://github.com/projectlombok) | Java boilerplate reduction annotations |

---

## License

This project is licensed under the [MIT License](LICENCE).
