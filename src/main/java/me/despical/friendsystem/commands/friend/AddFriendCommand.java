package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.handlers.ChatManager;
import me.despical.friendsystem.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class AddFriendCommand extends SubCommand {

	public AddFriendCommand() {
		super("add");
	}

	@Override
	public String getPossibleArguments() {
		return "<player>";
	}

	@Override
	public int getMinimumArguments() {
		return 1;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		ChatManager chatManager = plugin.getChatManager();
		User user = plugin.getUserManager().getUser((Player) sender);

		if (Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(chatManager.colorMessage("Commands.No-Player").replace("%name%", args[0]));
			return;
		}

		User friend = plugin.getUserManager().getUser(Bukkit.getPlayerExact(args[0]));

		if (user.isFriendWith(friend)) {
			sender.sendMessage(chatManager.colorMessage("Commands.Already-Friend"));
			return;
		}

		if (user.isIgnored(friend)) {
			sender.sendMessage(chatManager.colorMessage("No-Request-For-Ignored"));
			return;
		}

		if (friend.isIgnored(user)) {
			sender.sendMessage(chatManager.colorMessage("Commands.You-Are-Ignored-By-Them"));
			return;
		}

		if (!friend.isAcceptingRequests()) {
			sender.sendMessage(chatManager.colorMessage("Commands.Not-Accepting-Requests"));
			return;
		}

		if (friend.getRequests().contains(user.getPlayer().getUniqueId().toString())) {
			sender.sendMessage(chatManager.colorMessage("Commands.Already-Sent").replace("%player%", args[0]));
			return;
		}

		user.addFriend(friend);

		sender.sendMessage(chatManager.colorMessage("Commands.Send-Request").replace("%player%", args[0]));
		friend.getPlayer().sendMessage(chatManager.colorMessage("Commands.Recieve-Request").replace("%player%", sender.getName()));

		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			if (!user.isFriendWith(friend)) {
				friend.denyUser(user);

				sender.sendMessage(chatManager.colorMessage("Commands.Request-Expired").replace("%player%", args[0]));
				friend.getPlayer().sendMessage(chatManager.colorMessage("Commands.Time-Expired-To-Accept").replace("%player%", sender.getName()));
			}
		}, 6000);
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}