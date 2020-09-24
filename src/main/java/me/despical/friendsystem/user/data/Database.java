package me.despical.friendsystem.user.data;

import me.despical.friendsystem.user.User;

import java.util.List;

public interface Database {

	void addFriend(User friend);

	void acceptUser(User user);

	void removeFriend(User friend, boolean ignored);

	void denyUser(User user);

	void toggleRequests();

	void toggleNotifications();

	List<String> getRequests();

//	List<User> getActiveFriends();

	boolean isFriendWith(User user);

	boolean isIgnored(User user);

	boolean isAcceptingRequests();

	boolean isAcceptingNotifications();

	boolean hasRequestFrom(User user);
}
