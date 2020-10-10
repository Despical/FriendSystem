package me.despical.friendsystem.user.data;

import me.despical.friendsystem.user.User;

import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public interface Database {

	void addFriend(User friend);

	void acceptUser(User user);

	void removeFriend(User friend, boolean ignored);

	void denyUser(User user);

	void toggleRequests();

	void toggleNotifications();

	List<String> getRequests();

	boolean isFriendWith(User user);

	boolean isIgnored(User user);

	boolean isAcceptingRequests();

	boolean isAcceptingNotifications();

	boolean hasRequestFrom(User user);
}