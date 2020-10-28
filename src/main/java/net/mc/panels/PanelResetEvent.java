package net.mc.panels;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Scoreboard;

import lombok.Getter;

public class PanelResetEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
	
    @Getter
    private Panel panel;
    
    @Getter
    private Scoreboard oldScoreboard;
    
	public PanelResetEvent(Panel panel, Scoreboard oldScoreboard) {
		this.panel = panel;
		this.oldScoreboard = oldScoreboard;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
