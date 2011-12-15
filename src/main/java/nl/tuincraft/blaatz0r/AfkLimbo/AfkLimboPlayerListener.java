package nl.tuincraft.blaatz0r.AfkLimbo;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;


public class AfkLimboPlayerListener extends PlayerListener {

	public AfkLimbo plugin;
	
	public AfkLimboPlayerListener(AfkLimbo plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerJoin (PlayerJoinEvent event) {
		Player p = event.getPlayer();
		plugin.afk.playerTimes.put(p, System.currentTimeMillis());
		
		if (p.getWorld().getName() == plugin.limboWorld) {
			tpBack(p);
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event)  {
		plugin.afk.playerTimes.remove(event.getPlayer());
	}
	
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		updatePlayer(event.getPlayer());
	}
		
	public void onPlayerChat (PlayerChatEvent event) {
		updatePlayer(event.getPlayer());
	}
	
	public void onPlayerInteract (PlayerInteractEvent event) {
		updatePlayer(event.getPlayer());
	}
	
	public void onPlayerInteractEntity (PlayerInteractEntityEvent event) {
		updatePlayer(event.getPlayer());
	}	
	
	public void onPlayerMove (PlayerMoveEvent event) {
		updatePlayer(event.getPlayer());
	}
	
	public void onPlayerAnimation(PlayerAnimationEvent event) {
		updatePlayer(event.getPlayer());
	}
		
	public void tpBack(Player p) {
		Location dest = plugin.afk.lastLoc.containsKey(p) ? plugin.afk.lastLoc.get(p) : plugin.getServer().getWorld(plugin.defaultWorld).getSpawnLocation();
		p.teleport(dest);
	}
	
	public void updatePlayer(Player p) {
		
		// If player is in limbo and elapsed time > threshold
		if (p.getWorld().getName() == plugin.limboWorld && plugin.afk.limboTimes.containsKey(p) && System.currentTimeMillis() - plugin.afk.limboTimes.get(p)> 5000) { 
			tpBack(p);
        	plugin.getServer().broadcastMessage(p.getName() + " has returned from limbo.");
		}
		
		plugin.afk.playerTimes.put(p, System.currentTimeMillis());
		
	}
	
}
