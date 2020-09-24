package me.despical.friendsystem.user;

import me.despical.friendsystem.utils.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UserManager {

	private final List<User> users;

	public UserManager() {
		this.users = new ArrayList<>();

		this.loadFriendsForOnlinePlayers();
	}

	private void loadFriendsForOnlinePlayers() {
		Bukkit.getOnlinePlayers().forEach(player -> getUser(player).loadFriends());
	}

	public User getUser(Player player) {
		for (User user : users) {
			if (user.getPlayer().equals(player)) {
				return user;
			}
		}

		User user = new User(player);
		users.add(user);

		Debugger.debug(Level.INFO, "Registered new user {0} ({1})", player.getUniqueId(), player.getName());

		return user;
	}

	public void removeUser(User user) {
		users.remove(user);
	}
}
