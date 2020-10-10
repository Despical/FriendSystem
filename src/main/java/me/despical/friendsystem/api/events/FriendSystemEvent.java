package me.despical.friendsystem.api.events;

import me.despical.friendsystem.user.User;
import org.bukkit.event.Event;

/**
 * @author Despical
 * @since 0.0.1-ALPHA
 * <p>
 * Created at 22.09.2020
 */
public abstract class FriendSystemEvent extends Event {

	protected User user;

	public FriendSystemEvent(User user) {
		this.user = user;
	}

	public User getPlayer() {
		return user;
	}
}