package dev.westernpine.panels;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import dev.westernpine.panels.util.Strings;
import lombok.Getter;

public class Panel {
	
	@Getter
	private Player player;
	
	@Getter
	private boolean adjustable;
	
	@Getter
	private boolean teamsAdjustable;
	
	@Getter
	private Properties properties;
	
	private BiConsumer<Team, Player> viewedAsHandler;
	
	private BiConsumer<Team, Player> viewOfHandler;
	
	public Panel(Player player) {
		this.player = player;
		this.adjustable = true;
		this.teamsAdjustable = true;
		this.properties = new Properties();
	}

	public TextBuilder text() {
		return new TextBuilder(this);
	}

	public Objective objective(DisplaySlot slot, String text, Object... values) {
		Objective objective = getScoreboard()
						.registerNewObjective("name", "", Strings.formatAndColor(text, values));

		objective.setDisplaySlot(slot);

		return objective;
	}

	public Optional<BiConsumer<Team, Player>> getViewedAsHandler() {
		return Optional.ofNullable(this.viewedAsHandler);
	}
	
	public Panel setViewedAsHandler(BiConsumer<Team, Player> viewedAsHandler) {
		this.viewedAsHandler = viewedAsHandler;
		return this;
	}
	
	public Optional<BiConsumer<Team, Player>> getViewOfHandler() {
		return Optional.ofNullable(this.viewOfHandler);
	}
	
	public Panel setViewOfHanldler(BiConsumer<Team, Player> viewOfHandler) {
		this.viewOfHandler = viewOfHandler;
		return this;
	}
	
	void clear() {
		if(player.getScoreboard() == null)
			return;
		player.getScoreboard().getObjectives().forEach(obj -> obj.unregister());
		player.getScoreboard().getTeams().forEach(team -> team.unregister());
	}
	
	public Panel setAdjustable(boolean adjustable) {
		this.adjustable = adjustable;
		return this;
	}
	
	public Panel setTeamsAdjustable(boolean teamsAdjustable) {
		this.teamsAdjustable = teamsAdjustable;
		return this;
	}
	
	public Panel setOverallAdjustable(boolean adjustable) {
		this.teamsAdjustable = adjustable;
		this.adjustable = adjustable;
		return this;
	}
	
	public boolean isOverallAdjustable() {
		return adjustable && teamsAdjustable;
	}
	
	public boolean isEitherAdjustable() {
		return adjustable || teamsAdjustable;
	}
	
	public Scoreboard getScoreboard() {
		return player.getScoreboard();
	}
	
	public boolean isDefault() {
		return getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard());
	}
	
	public Panel applyOrderedObjectiveScores(Objective objective, LinkedList<String> orderedStrings, int startScore) {
		for(int i = 0; i < orderedStrings.size(); i++)
			objective.getScore(orderedStrings.get(orderedStrings.size()-1-i)).setScore(startScore + i);
		return this;
	}
	
	private void processOrDispose(Optional<BiConsumer<Team, Player>> optional, Team team, Player player) {
		if(optional.isPresent()) {
			optional.get().accept(team, player);
			team.addEntry(player.getName());
		} else {
			team.unregister();
		}
	}
	
	public Panel resetTeams() {
		Scoreboard scoreboard = getScoreboard();
		if(scoreboard != null)
			scoreboard.getTeams().forEach(team -> team.unregister());
		Bukkit.getPluginManager().callEvent(new PanelTeamsResetEvent(this));
		Player player = getPlayer();
		scoreboard = getScoreboard() != null ? getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
		for(Panel localPanel : Panels.getPanels()) {
			if(localPanel.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				Scoreboard localScoreboard = localPanel.getScoreboard() != null ? localPanel.getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
		    	Team localTeam = localScoreboard.getTeam(player.getName()) != null ? localScoreboard.getTeam(player.getName()) : localScoreboard.registerNewTeam(player.getName());
		    	processOrDispose(getViewedAsHandler(), localTeam, player);
			} else {
				Team team = scoreboard.getTeam(localPanel.getPlayer().getName()) != null ? scoreboard.getTeam(localPanel.getPlayer().getName()) : scoreboard.registerNewTeam(localPanel.getPlayer().getName());
				processOrDispose(getViewOfHandler(), team, localPanel.getPlayer());
			}
		}
		return this;
	}
	
	public Panel reset() {
		return reset(true);
	}
	
	public Panel reset(boolean resetTeams) {
		Scoreboard oldScoreboard = getScoreboard();
		Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(newScoreboard);
		if (resetTeams)
			resetTeams();
		else
			Panels.accept(localPanel -> processOrDispose(getViewOfHandler(),
					newScoreboard.getTeam(localPanel.getPlayer().getName()) != null
							? newScoreboard.getTeam(localPanel.getPlayer().getName())
							: newScoreboard.registerNewTeam(localPanel.getPlayer().getName()),
					localPanel.getPlayer()));
		Bukkit.getPluginManager().callEvent(new PanelResetEvent(this, oldScoreboard));
		return this;
	}
	
	public Panel perform(Consumer<Panel> handler) {
		handler.accept(this);
		return this;
	}
	
	public <T> T transform(Function<Panel, T> handler) {
		return handler.apply(this);
	}
	
	public Panel accept(Consumer<Scoreboard> handler) {
		handler.accept(getScoreboard());
		return this;
	}
	
	public <T> T apply(Function<Scoreboard, T> handler) {
		return handler.apply(getScoreboard());
	}

}
