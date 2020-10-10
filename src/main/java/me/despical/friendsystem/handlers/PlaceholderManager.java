package me.despical.friendsystem.handlers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.despical.friendsystem.api.FriendsAPI;
import me.despical.friendsystem.user.User;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 10.10.2020
 */
public class PlaceholderManager extends PlaceholderExpansion {

	@Override
	public boolean persist() {
		return true;
	}

	public String getIdentifier() {
		return "friendsystem";
	}

	public String getAuthor() {
		return "Despical";
	}

	public String getVersion() {
		return "1.0.0";
	}

	public String onPlaceholderRequest(Player player, String id) {
		if (player == null) {
			return null;
		}

		User user = FriendsAPI.getUser(player);

		switch (id.toLowerCase()) {
			case "friends":
				return Integer.toString(user.getFriends().size());
			case "online_friends":
				return Integer.toString(user.getFriends().stream().filter(p -> p.getPlayer().isOnline()).toArray().length);
			default:
				return null;
		}
	}
}