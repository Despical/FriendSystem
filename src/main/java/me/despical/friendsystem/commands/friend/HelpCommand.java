package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.commands.exception.CommandException;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

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
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {

	}

	@Override
	public List<String> getTutorial() {
		return Collections.singletonList("Prints all available friend commands");
	}

	@Override
	public CommandType getType() {
		return CommandType.BOTH;
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.BOTH;
	}
}
