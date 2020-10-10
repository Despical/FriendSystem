package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class ToggleNotificationsCommand extends SubCommand {

	public ToggleNotificationsCommand() {
		super("notifications", "notifics", "notifs");
	}

	@Override
	public String getPossibleArguments() {
		return null;
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		User user = plugin.getUserManager().getUser((Player) sender);
		String notifications = user.isAcceptingNotifications() ? "Enabled" : "Disabled";

		user.toggleNotifications();

		sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Notifications").replace("%notifications%", notifications));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}