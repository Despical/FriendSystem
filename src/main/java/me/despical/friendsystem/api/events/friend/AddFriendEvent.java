package me.despical.friendsystem.api.events.friend;

import me.despical.friendsystem.api.events.FriendSystemEvent;
import me.despical.friendsystem.user.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class AddFriendEvent extends FriendSystemEvent implements Cancellable {

	private final HandlerList HANDLERS = new HandlerList();
	private final User friend;
	private boolean isCancelled;

	public AddFriendEvent(User user, User friend) {
		super(user);
		this.friend = friend;
		this.isCancelled = false;
	}

	public User getFriend() {
		return friend;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
