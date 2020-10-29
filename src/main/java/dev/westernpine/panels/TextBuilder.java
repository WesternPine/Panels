package dev.westernpine.panels;

import dev.westernpine.panels.util.Strings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;

public class TextBuilder extends ArrayList<String> {

  private final Panel panel;

  public TextBuilder(Panel panel) {
    this.panel = panel;
  }

  public TextBuilder next(String text, Object... values) {
    add(Strings.formatAndColor(text, values));

    return this;
  }

  public Panel apply(Objective objective) {
    return apply(objective, 0);
  }

  public Panel apply(Objective objective, int start) {
    for(int i = 0; i < size(); i++) {
      objective.getScore(get(size()-1-i)).setScore(start + i);
    }

    return panel;
  }
}
