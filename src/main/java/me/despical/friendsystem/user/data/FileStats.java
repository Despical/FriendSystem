package me.despical.friendsystem.user.data;

import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class FileStats implements Database {

	private final Main plugin;
	private final User user;
	private final FileConfiguration config;

	public FileStats(Main plugin, User user) {
		this.plugin = plugin;
		this.user = user;
		this.config = ConfigUtils.getConfig(plugin, "friends");
	}

	// TODO: implement messages

	@Override
	public void addFriend(User friend) {
		List<String> friends = config.getStringList(friend.getPlayer().getUniqueId().toString() + ".friends");
		friends.add(friend.getPlayer().getUniqueId().toString());

		config.set(friend.getPlayer().getUniqueId().toString() + ".waiting-requests", friends);

		saveConfig();
	}

	@Override
	public void acceptUser(User user) {
		List<String> requests = config.getStringList(user.getPlayer().getUniqueId().toString() + ".waiting-requests");
		requests.remove(user.getPlayer().getUniqueId().toString());

		config.set(user.getPlayer().getUniqueId().toString() + ".waiting-requests", requests);

		List<String> friends = config.getStringList(this.user.getPlayer().getUniqueId().toString() + ".friends");
		friends.add(user.getPlayer().getUniqueId().toString());

		config.set(this.user.getPlayer().getUniqueId().toString() + ".friends", friends);

		List<String> userFriends = config.getStringList(user.getPlayer().getUniqueId().toString() + ".friends");
		userFriends.add(this.user.getPlayer().getUniqueId().toString());

		config.set(user.getPlayer().getUniqueId().toString() + ".friends", userFriends);

		saveConfig();
	}

	@Override
	public void removeFriend(User friend, boolean ignored) {
		List<String> friends = config.getStringList(this.user.getPlayer().getUniqueId().toString() + ".friends");
		friends.remove(friend.getPlayer().getUniqueId().toString());

		config.set(this.user.getPlayer().getUniqueId().toString() + ".friends", friends);

		if (ignored) {
			List<String> ignoredUsers = config.getStringList(this.user.getPlayer().getUniqueId().toString() + ".ignored-users");
			ignoredUsers.add(friend.getPlayer().getUniqueId().toString());

			config.set(this.user.getPlayer().getUniqueId().toString() + ".ignored-users", ignoredUsers);
		}

		saveConfig();
	}

	@Override
	public void denyUser(User user) {
		List<String> requests = config.getStringList(this.user.getPlayer().getUniqueId().toString() + ".waiting-requests");
		requests.remove(user.getPlayer().getUniqueId().toString());

		config.set(this.user.getPlayer().getUniqueId().toString() + ".waiting-requests", requests);

		saveConfig();
	}

	@Override
	public void toggleRequests() {
		String uuid = user.getPlayer().getUniqueId().toString();

		config.set(uuid + ".accepting-requests", !config.getBoolean(uuid + ".accepting-requests"));

		saveConfig();
	}

	@Override
	public void toggleNotifications() {
		String uuid = user.getPlayer().getUniqueId().toString();

		config.set(uuid + ".accepting-notifications", !config.getBoolean(uuid + ".accepting-notifications"));

		saveConfig();
	}

	@Override
	public List<String> getRequests() {
		return config.getStringList(user.getPlayer().getUniqueId().toString() + ".waiting-requests");
	}

	@Override
	public boolean isFriendWith(User user) {
		return config.contains(this.user.getPlayer().getUniqueId().toString() + ".friends." + user.getPlayer().getUniqueId().toString());
	}

	@Override
	public boolean isIgnored(User user) {
		return config.contains(this.user.getPlayer().getUniqueId().toString() + ".ignored-users." + user.getPlayer().getUniqueId().toString());
	}

	@Override
	public boolean isAcceptingRequests() {
		return config.getBoolean(user.getPlayer().getUniqueId().toString() + ".accepting-requests");
	}

	@Override
	public boolean isAcceptingNotifications() {
		return config.getBoolean(user.getPlayer().getUniqueId().toString() + ".accepting-notifications");
	}

	@Override
	public boolean hasRequestFrom(User user) {
		return config.contains(this.user.getPlayer().getUniqueId().toString() + ".waiting-requests." + user.getPlayer().getUniqueId().toString());
	}

	private void saveConfig() {
		ConfigUtils.saveConfig(plugin, config, "friends");
	}
}
