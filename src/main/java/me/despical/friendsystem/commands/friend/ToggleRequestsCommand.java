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
public class ToggleRequestsCommand extends SubCommand {

	public ToggleRequestsCommand() {
		super("toggle");
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
		String requests = user.isAcceptingRequests() ? "Enabled" : "Disabled";

		user.toggleRequests();

		sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Toggle-Requests").replace("%requests%", requests));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}