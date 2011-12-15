package nl.tuincraft.blaatz0r.AfkLimbo;

import java.util.logging.Logger;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AfkLimbo extends JavaPlugin {
	private final AfkLimboPlayerListener playerListener = new AfkLimboPlayerListener(this);
    public static Logger log;    
    
    public String name;
    public String version;
    
    public AfkPoller afk;
    public WorldCreator w;
    public World limbo;
   
    public FileConfiguration cfg;
    
    public String limboWorld;
    public String defaultWorld;
    public int afkTime;
    
	public void onDisable() {
        log = Logger.getLogger("Minecraft");
        log.info(name + " " + version + " disabled");
		
	}
	
	public void onEnable() {
        this.getDataFolder().mkdir();
        
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
				
		cfg = getConfig();
		
		limboWorld = cfg.getString("limbo-world", "world_limbo");
		defaultWorld = cfg.getString("default-world", "world");
		afkTime = cfg.getInt("afk-time", 60);
		
		this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, afk = new AfkPoller(this, afkTime), 20L, 20L);
				                
        PluginManager pm = getServer().getPluginManager();        

        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, playerListener, Priority.Normal, this);
        
        log = Logger.getLogger("Minecraft");
        log.info(name + " " + version + " enabled");
        
        // Create the limbo world
        w = new WorldCreator(limboWorld);
        w.environment(World.Environment.NORMAL);
        limbo = this.getServer().createWorld(w);
        limbo.setDifficulty(Difficulty.PEACEFUL);
        limbo.setPVP(false);
		
	}

	
}
