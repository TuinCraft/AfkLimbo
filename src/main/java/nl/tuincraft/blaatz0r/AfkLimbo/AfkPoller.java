package nl.tuincraft.blaatz0r.AfkLimbo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AfkPoller implements Runnable {

	public AfkLimbo plugin;
	public int time;
	
	HashMap<Player, Long> playerTimes;
	HashMap<Player, Long> limboTimes;
	HashMap<Player, Location> lastLoc;
	
	public AfkPoller(AfkLimbo plugin, int time) {
		this.plugin = plugin;
		this.time = time;
		
		playerTimes = new HashMap<Player, Long>();
		limboTimes = new HashMap<Player, Long>();
		lastLoc = new HashMap<Player, Location>();
	}

	public void run() {
		
		for (Entry<Player, Long> pairs : playerTimes.entrySet()) {
	    	Player p = pairs.getKey();
	        long diff = System.currentTimeMillis() - pairs.getValue();
	        
	        if (diff > time*1000) {
	        	
	        	// If player not in limbo and is online
	        	if (p.getWorld().getName() != plugin.limboWorld && p.isOnline()) {
	        		
	        		// Save his last location
	        		lastLoc.put(p, p.getLocation());
	        		// Save time he went to limbo
	        		limboTimes.put(p, System.currentTimeMillis());
	        		// TP him to limbo
		        	p.teleport(plugin.limbo.getSpawnLocation());
		        	// Broadcast a message about it
		        	plugin.getServer().broadcastMessage(p.getName() + " has entered limbo.");
	        	}
	        }
	        
	    }
	}

}
