package dev.westernpine.panels.util;

import org.bukkit.ChatColor;

public class Strings {

  public static String color(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public static String format(String text, Object... values) {
    for (int i = 0; i < values.length; i++) {
      text = text.replace("{" + i + "}", toSafeString(values[i]));
    }
    return text;
  }

  public static String formatAndColor(String text, Object... values) {
    return color(format(text, values));
  }

  public static String toSafeString(Object value) {
    return value != null ? value.toString() : "null";
  }
}
