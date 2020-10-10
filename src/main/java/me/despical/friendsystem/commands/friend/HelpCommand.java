package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.handlers.ChatManager;
import org.bukkit.command.CommandSender;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class HelpCommand extends SubCommand {

	public HelpCommand() {
		super("help");
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
		ChatManager chatManager = plugin.getChatManager();

		sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Header"));
		sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Description").replace("%cmd%", label));

		if (sender.hasPermission("friendsystem.admin")) {
			sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Admin-Bonus-Description").replace("%cmd%", label));
		}

		sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Footer"));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.BOTH;
	}
}