package net.mc.panels;

import java.util.Optional;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PanelListener implements Listener {
	
	public void processOrDispose(Optional<BiConsumer<Team, Player>> optional, Team team, Player player) {
		if(optional.isPresent()) {
			optional.get().accept(team, player);
			team.addEntry(player.getName());
		} else {
			team.unregister();
		}
	}
	
	@EventHandler
	public void onPanelTeamsReset(PanelTeamsResetEvent event) {
		Panel panel = event.getPanel();
		Player player = panel.getPlayer();
		Scoreboard scoreboard = panel.getScoreboard() != null ? panel.getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
		for(Panel localPanel : Panels.getPanels()) {
			if(localPanel.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				Scoreboard localScoreboard = localPanel.getScoreboard() != null ? localPanel.getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
		    	Team localTeam = localScoreboard.getTeam(player.getName()) != null ? localScoreboard.getTeam(player.getName()) : localScoreboard.registerNewTeam(player.getName());
		    	processOrDispose(panel.getViewedAsHandler(), localTeam, player);
				localPanel.getPlayer().setScoreboard(localScoreboard);
			} else {
				Team team = scoreboard.getTeam(localPanel.getPlayer().getName()) != null ? scoreboard.getTeam(localPanel.getPlayer().getName()) : scoreboard.registerNewTeam(localPanel.getPlayer().getName());
				processOrDispose(panel.getViewOfHandler(), team, localPanel.getPlayer());
				player.setScoreboard(scoreboard);
			}
		}
	}
	
}
