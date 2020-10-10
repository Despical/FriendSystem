package me.despical.friendsystem.events;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import me.despical.friendsystem.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Despical
 * <p>
 * Created at 10.10.2020
 */
public class JoinEvent implements Listener {

	private final Main plugin;

	public JoinEvent(Main plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		User user = plugin.getUserManager().getUser(event.getPlayer());

		for (User friend : user.getFriends()) {
			if (friend.isAcceptingNotifications()) {
				friend.getPlayer().sendMessage(plugin.getChatManager().colorMessage("Friend-Messages.Join-Notification").replace("%player%", event.getPlayer().getName()));
			}
		}
	}

	@EventHandler
	public void onJoinCheckVersion(final PlayerJoinEvent event) {
		if (!plugin.getConfig().getBoolean("Update-Notifier.Enabled", true) || !event.getPlayer().hasPermission("friendsystem.updatenotify")) {
			return;
		}

		Bukkit.getScheduler().runTaskLater(plugin, () -> UpdateChecker.init(plugin, 1).requestUpdateCheck().whenComplete((result, exception) -> {
			if (!result.requiresUpdate()) {
				return;
			}

			if (result.getNewestVersion().contains("b")) {
				event.getPlayer().sendMessage(plugin.getChatManager().colorRawMessage("&3[FriendSystem] &bFound a beta update: v" + result.getNewestVersion() + " Download it on Spigot"));
			} else {
				event.getPlayer().sendMessage(plugin.getChatManager().colorRawMessage("&3[FriendSystem] &bFound an update: v" + result.getNewestVersion() + " Download it on Spigot"));
			}
		}), 25);
	}
}