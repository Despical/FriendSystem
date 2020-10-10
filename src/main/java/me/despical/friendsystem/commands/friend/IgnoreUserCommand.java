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
public class IgnoreUserCommand extends SubCommand {

	public IgnoreUserCommand() {
		super("ignore");
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

		if (user.isIgnored(friend)) {
			sender.sendMessage(chatManager.colorMessage("Commands.Already-Ignored"));
			return;
		}

		user.removeFriend(friend, true);

		sender.sendMessage(chatManager.colorMessage("Commands.Player-Ignored").replace("%player%", args[0]));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}