package me.despical.friendsystem.user;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.api.events.friend.AddFriendEvent;
import me.despical.friendsystem.api.events.friend.RemoveFriendEvent;
import me.despical.friendsystem.user.data.Database;
import me.despical.friendsystem.user.data.FileStats;
import me.despical.friendsystem.user.data.MysqlManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class User {

	private final Player player;
	private final Main plugin;
	private final List<User> friends;
	private final Database database;

	public User(Player player) {
		this.player = player;
		this.plugin = JavaPlugin.getPlugin(Main.class);
		this.friends = new ArrayList<>();
		this.database = plugin.getConfig().getBoolean("DatabaseActivated", false) ? new MysqlManager(plugin, this) : new FileStats(plugin, this);
	}

	public Player getPlayer() {
		return player;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void addFriend(User friend) {
		database.addFriend(friend);

		Bukkit.getScheduler().runTask(plugin, () -> {
			AddFriendEvent addFriendEvent = new AddFriendEvent(this, friend);

			Bukkit.getPluginManager().callEvent(addFriendEvent);
		});
	}

	public void acceptFriend(User friend) {
		database.acceptUser(friend);
	}

	public void removeFriend(User friend, boolean ignored) {
		database.removeFriend(friend, ignored);

		Bukkit.getScheduler().runTask(plugin, () -> {
			RemoveFriendEvent removeFriendEvent = new RemoveFriendEvent(this, friend, ignored);

			Bukkit.getPluginManager().callEvent(removeFriendEvent);
		});
	}

	public void denyUser(User user) {
		database.denyUser(user);
	}

	public void toggleRequests() {
		database.toggleRequests();
	}

	public void toggleNotifications() {
		database.toggleNotifications();
	}

	public List<String> getRequests() {
		return database.getRequests();
	}

	public boolean isFriendWith(User user) {
		return database.isFriendWith(user);
	}
	
	public boolean isIgnored(User user) {
		return database.isIgnored(user);
	}

	public boolean isAcceptingRequests() {
		return database.isAcceptingRequests();
	}

	public boolean isAcceptingNotifications() {
		return database.isAcceptingNotifications();
	}

	public boolean hasRequestFrom(User user) {
		return database.hasRequestFrom(user);
	}
}