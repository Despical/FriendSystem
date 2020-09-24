package me.despical.friendsystem.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.friendsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChatManager {

	private final Main plugin;
	private String prefix;
	private FileConfiguration config;

	public ChatManager(Main plugin) {
		this.plugin = plugin;
		this.config = ConfigUtils.getConfig(plugin, "messages");
		this.prefix = colorRawMessage(config.getString(""));
	}

	public String getPrefix() {
		return prefix;
	}

	public String colorRawMessage(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public String colorMessage(String path) {
		return colorRawMessage(config.getString(path));
	}

	private String colorMessage(String path, Player player) {
		String message = config.getString(path);

		if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			message = PlaceholderAPI.setPlaceholders(player, message);
		}

		return colorRawMessage(message);
	}



	public void reloadConfig() {
		config = ConfigUtils.getConfig(plugin, "messages");
		prefix = colorRawMessage(config.getString(""));
	}
}
