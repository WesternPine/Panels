package net.mc.panels;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

public class PanelTeamsResetEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
	
    @Getter
    private Panel panel;
    
	public PanelTeamsResetEvent(Panel panel) {
		this.panel = panel;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
