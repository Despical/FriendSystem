package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.commands.exception.CommandException;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class FriendsListCommand extends SubCommand {

	public FriendsListCommand() {
		super("list");
	}

	@Override
	public String getPossibleArguments() {
		return "[page]";
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {

	}

	@Override
	public List<String> getTutorial() {
		return Collections.singletonList("List your friends");
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
