package net.mc.panels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayeJoin(PlayerJoinEvent event) {
		Panels.get(event.getPlayer()).reset();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Panels.dispose(event.getPlayer());
	}
	
}
