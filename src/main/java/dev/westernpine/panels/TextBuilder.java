package dev.westernpine.panels;

import java.util.ArrayList;

import org.bukkit.scoreboard.Objective;

import dev.westernpine.panels.util.Strings;

public class TextBuilder extends ArrayList<String> {
	private static final long serialVersionUID = 1L;
	
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
