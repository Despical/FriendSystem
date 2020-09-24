package me.despical.friendsystem.api.events;

import me.despical.friendsystem.user.User;
import org.bukkit.event.Event;

public abstract class FriendSystemEvent extends Event {

	protected User user;

	public FriendSystemEvent(User user) {
		this.user = user;
	}

	public User getPlayer() {
		return user;
	}
}
