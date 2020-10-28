package net.mc.panels;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import lombok.Getter;

public class Panel {
	
	@Getter
	private Player player;
	
	@Getter
	private boolean adjustable;
	
	@Getter
	private Properties properties;
	
	private BiConsumer<Team, Player> viewedAsHandler;
	
	private BiConsumer<Team, Player> viewOfHandler;
	
	public Panel(Player player) {
		this.player = player;
		this.adjustable = true;
		this.properties = new Properties();
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
	
	public Panel callTeamsReset() {
		Scoreboard scoreboard = getScoreboard();
		if(scoreboard != null)
			scoreboard.getTeams().forEach(team -> team.unregister());
		Bukkit.getPluginManager().callEvent(new PanelTeamsResetEvent(this));
		return this;
	}
	
	public Panel reset() {
		Scoreboard oldScoreboard = getScoreboard();
		Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		player.setScoreboard(newScoreboard);
		callTeamsReset();
		Bukkit.getPluginManager().callEvent(new PanelResetEvent(this, oldScoreboard));
		player.setScoreboard(getScoreboard());
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
