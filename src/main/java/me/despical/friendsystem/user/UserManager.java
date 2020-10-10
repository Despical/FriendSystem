package me.despical.friendsystem.user;

import me.despical.friendsystem.utils.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class UserManager {

	private final List<User> users;

	public UserManager() {
		this.users = new ArrayList<>();

		Bukkit.getServer().getOnlinePlayers().forEach(this::getUser);
	}

	public User getUser(Player player) {
		for (User user : users) {
			if (user.getPlayer().equals(player)) {
				return user;
			}
		}

		User user = new User(player);
		users.add(user);

		Debugger.debug("Registered new user {0} ({1})", player.getUniqueId(), player.getName());
		return user;
	}

	public void removeUser(User user) {
		users.remove(user);
	}
}
