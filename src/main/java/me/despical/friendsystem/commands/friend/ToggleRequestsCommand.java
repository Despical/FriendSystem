package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.commands.exception.CommandException;
import me.despical.friendsystem.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

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
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		User user = getPlugin().getUserManager().getUser((Player) sender);

		user.toggleRequests();

		getPlugin().getChatManager().colorMessage("");
	}

	@Override
	public List<String> getTutorial() {
		return Collections.singletonList("Toggles friend requests");
	}

	@Override
	public CommandType getType() {
		return CommandType.BOTH;
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}
