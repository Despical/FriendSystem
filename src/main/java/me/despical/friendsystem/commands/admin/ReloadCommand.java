package me.despical.friendsystem.commands.admin;

import me.despical.friendsystem.commands.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author Despical
 * <p>
 * Created at 10.10.2020
 */
public class ReloadCommand extends SubCommand {

	public ReloadCommand() {
		super("reload", "rl");

		setPermission("friendsystem.admin");
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
		plugin.reloadConfig();
		plugin.getChatManager().reloadConfig();

		sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Reload-Configuration"));
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.BOTH;
	}
}