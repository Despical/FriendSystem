package me.despical.friendsystem.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import me.despical.commonsbox.compat.VersionResolver;
import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.commonsbox.string.StringMatcher;
import me.despical.friendsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class ChatManager {

	private final Main plugin;
	private FileConfiguration config;

	public ChatManager(Main plugin) {
		this.plugin = plugin;
		this.config = ConfigUtils.getConfig(plugin, "messages");
	}

	public String colorRawMessage(String message) {
		if (message == null) {
			return "";
		}

		if (message.contains("#") && VersionResolver.isCurrentEqualOrHigher(VersionResolver.ServerVersion.v1_16_R1)) {
			message = StringMatcher.matchColorRegex(message);
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public String colorMessage(String message) {
		return colorRawMessage(config.getString(message));
	}

	public String colorMessage(String message, Player player) {
		String returnString = config.getString(message);

		if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			returnString = PlaceholderAPI.setPlaceholders(player, returnString);
		}

		return colorRawMessage(returnString);
	}

	public void reloadConfig() {
		config = ConfigUtils.getConfig(plugin, "messages");
	}
}