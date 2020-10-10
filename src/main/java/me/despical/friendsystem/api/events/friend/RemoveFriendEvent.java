package me.despical.friendsystem.api.events.friend;

import me.despical.friendsystem.api.events.FriendSystemEvent;
import me.despical.friendsystem.user.User;
import org.bukkit.event.HandlerList;

/**
 * @author Despical
 * @since 0.0.1-ALPHA
 * <p>
 * Created at 22.09.2020
 */
public class RemoveFriendEvent extends FriendSystemEvent {

	private final HandlerList HANDLERS = new HandlerList();
	private final User friend;
	private final boolean ignored;

	public RemoveFriendEvent(User user, User friend, boolean ignored) {
		super(user);
		this.friend = friend;
		this.ignored = ignored;
	}

	public User getFriend() {
		return friend;
	}

	public boolean isIgnored() {
		return ignored;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
}
