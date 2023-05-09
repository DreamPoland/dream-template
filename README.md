Working with template:
-----
Clone this repo to your account, rename package name, then by pressing ``CTRL + SHIFT + R`` change all variables to your plugin name. (Template --> PluginName).

Finally, remove all **unnecessary** template functions.
(support mc versions can easily removed by deleting implement in settings.gradle)

Build to jar by ``shadowJar`` gradle task.

**Required:**
-----
- Spigot-api 1.8.8, 1.12.2, 1.16.5, 1.17.1, 1.18.2, 1.19.3 (or implement by self)
- Java SDK. (java 11+, target 8)

Working with NMS:
-----
- Add ProtocolLib to build.gradle and work with it.
- Add nms into mc-version modules.

------

**Libraries**: <33
- [Dream-Platform](https://github.com/DreamPoland/dream-platform) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Menu](https://github.com/DreamPoland/dream-menu) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Command](https://github.com/DreamPoland/dream-command) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Notice](https://github.com/DreamPoland/dream-notice) by [DreamCode](https://github.com/DreamPoland)
- [Dream-Utilities](https://github.com/DreamPoland/dream-utilities) by [DreamCode](https://github.com/DreamPoland)
- [Okaeri-Configs](https://github.com/OkaeriPoland/okaeri-configs) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Persistence](https://github.com/OkaeriPoland/okaeri-persistence) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Injector](https://github.com/OkaeriPoland/okaeri-injector) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Placeholders](https://github.com/OkaeriPoland/okaeri-placeholders) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Tasker](https://github.com/OkaeriPoland/okaeri-tasker) by [Okaeri](https://github.com/OkaeriPoland)
- [XSeries](https://github.com/CryptoMorin/XSeries) by [CryptoMorin](https://github.com/CryptoMorin)
- and [Lombok](https://github.com/projectlombok/lombok) for clean dev by [ProjectLombok](https://github.com/projectlombok)
