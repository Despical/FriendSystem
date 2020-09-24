package me.despical.friendsystem.user.data;

import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MysqlManager implements Database {

	private final Main plugin;
	private final FileConfiguration config;

	public MysqlManager(Main plugin) {
		this.plugin = plugin;
		this.config = ConfigUtils.getConfig(plugin, "friends");
	}

	// TODO: add User constructor

	@Override
	public void addFriend(User friend) {

	}

	@Override
	public void acceptUser(User user) {

	}

	@Override
	public void removeFriend(User friend, boolean ignored) {

	}

	@Override
	public void denyUser(User user) {

	}

	@Override
	public void toggleRequests() {

	}

	@Override
	public void toggleNotifications() {

	}

	@Override
	public List<String> getRequests() {
		return null;
	}

	@Override
	public boolean isFriendWith(User user) {
		return false;
	}

	@Override
	public boolean isIgnored(User user) {
		return false;
	}

	@Override
	public boolean isAcceptingRequests() {
		return false;
	}

	@Override
	public boolean isAcceptingNotifications() {
		return false;
	}

	@Override
	public boolean hasRequestFrom(User user) {
		return false;
	}
}
