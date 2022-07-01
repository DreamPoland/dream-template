Working with template:
-----
Clone this repo to your account, rename package name, then by pressing ``CTRL + SHIFT + R`` change all variables to your plugin name. (Template --> PluginName).

Finally, remove all **unnecessary** template functions.

Build to jar by ``shadowJar`` gradle task.

**Required:**
-----
- Spigot 1.8x-1.19x
- Java 8-17.

**Example usage data:**
----------
```java
public class RandomActionHandler implements Listener {
    
    private @Inject UserRepoService userRepoService;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final User user = this.userRepoService.getOrCreate(player);
        
        user.getValue(); // return "first value"
        
        user.setValue("second value");
        user.save(); // required to save changes!
        
        user.getValue(); // return "second value"
    }
}
``` 
Thanks [Okaeri](https://github.com/OkaeriPoland) for [Object Document Mapping](https://github.com/OkaeriPoland/okaeri-persistence) library

------

**Libraries**: <33
- [Okaeri-Configs](https://github.com/OkaeriPoland/okaeri-configs) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Persistence](https://github.com/OkaeriPoland/okaeri-persistence) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Injector](https://github.com/OkaeriPoland/okaeri-injector) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Placeholders](https://github.com/OkaeriPoland/okaeri-placeholders) by [Okaeri](https://github.com/OkaeriPoland)
- [Okaeri-Tasker](https://github.com/OkaeriPoland/okaeri-tasker) by [Okaeri](https://github.com/OkaeriPoland)
- [FastBoard](https://github.com/MrMicky-FR/FastBoard) by [MrMicky](https://github.com/MrMicky-FR)
- [XSeries](https://github.com/MrMicky-FR) by [CryptoMorin](https://github.com/CryptoMorin)
- [Item-NBT-API](https://github.com/tr7zw/Item-NBT-API) by [tr7zw](https://github.com/tr7zw)
- [IridiumColorAPI](https://github.com/Iridium-Development/IridiumColorAPI) by [Iridium-Development](https://github.com/Iridium-Development)
- and [Lombok](https://github.com/projectlombok/lombok) for clean dev by [ProjectLombok](https://github.com/projectlombok)