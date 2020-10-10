package me.despical.friendsystem.events;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Despical
 * <p>
 * Created at 10.10.2020
 */
public class QuitEvent implements Listener {

	private final Main plugin;

	public QuitEvent(Main plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		User user = plugin.getUserManager().getUser(event.getPlayer());
		plugin.getUserManager().removeUser(user);

		for (User friend : user.getFriends()) {
			if (friend.isAcceptingNotifications()) {
				friend.getPlayer().sendMessage(plugin.getChatManager().colorMessage("Friend-Messages.Leave-Notification").replace("%player%", event.getPlayer().getName()));
			}
		}
	}
}