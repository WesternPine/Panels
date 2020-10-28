package dev.westernpine.panels;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class Panels extends JavaPlugin {
	
	@Getter
	private static Panels instance;
	
	private Map<UUID, Panel> panels;
	
	@Override
	public void onLoad() {
		instance = this;
		panels = new HashMap<>();
	}
	
	public String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	@Override
	public void onEnable() {
		Bukkit.getOnlinePlayers().forEach(player -> get(player).reset());
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(player -> dispose(player));
	}
	
	private Panel newPlayer(Player player) {
		Panel panel = new Panel(player);
		panels.put(player.getUniqueId(), panel);
		return panel;
	}
	
	private void discardPlayer(Player player) {
		get(player).setViewedAsHandler(null).setViewOfHanldler(null).reset();
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		panels.remove(player.getUniqueId());
	}
	
	public static void dispose(Player player) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		instance.discardPlayer(player);
	}
	
	public static Panel get(Player player) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		return instance.panels.get(player.getUniqueId()) != null ? instance.panels.get(player.getUniqueId()) : instance.newPlayer(player);
	}
	
	public static Collection<Panel> getPanels() {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		return instance.panels.values();
	}
	
	public static void accept(Consumer<Panel> handler) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		Iterator<Entry<UUID, Panel>> it = instance.panels.entrySet().iterator();
		while(it.hasNext())
			handler.accept(it.next().getValue());
	}
	
	public static <T> T accept(Consumer<Panel> handler, T toReturn) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		Iterator<Entry<UUID, Panel>> it = instance.panels.entrySet().iterator();
		while(it.hasNext())
			handler.accept(it.next().getValue());
		return toReturn;
	}
	
	public static void accept(Predicate<UUID> retainer, Consumer<Panel> handler) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		Iterator<Entry<UUID, Panel>> it = instance.panels.entrySet().iterator();
		while(it.hasNext()) {
			Entry<UUID, Panel> entry = it.next();
			if(retainer.test(entry.getKey()))
				handler.accept(entry.getValue());
		}
	}
	
	public static <T> T  accept(Predicate<UUID> retainer, Consumer<Panel> handler, T toReturn) {
		if(instance == null)
			throw new RuntimeException(new Exception("The Panels plugin isn't enabled!"));
		Iterator<Entry<UUID, Panel>> it = instance.panels.entrySet().iterator();
		while(it.hasNext()) {
			Entry<UUID, Panel> entry = it.next();
			if(retainer.test(entry.getKey()))
				handler.accept(entry.getValue());
		}
		return toReturn;
	}
	
}