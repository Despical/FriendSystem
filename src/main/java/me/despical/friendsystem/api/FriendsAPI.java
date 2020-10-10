package me.despical.friendsystem.api;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Despical
 * @since 0.0.2-ALPHA
 * <p>
 * Created at 10.10.2020
 */
public class FriendsAPI {

	private static Main plugin = JavaPlugin.getPlugin(Main.class);

	private FriendsAPI() {}

	public static User getUser(Player player) {
		return plugin.getUserManager().getUser(player);
	}
}