**Required:**
- Spigot 1.8x-1.19x
- Java 8-17.

**Example usage data:**
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
Build to jar by ``shadowJar`` gradle task.