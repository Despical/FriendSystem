package me.despical.friendsystem.commands.friend;

import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.commands.exception.CommandException;
import me.despical.friendsystem.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

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
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		User user = getPlugin().getUserManager().getUser((Player) sender);

		if (Bukkit.getPlayerExact(args[0]) == null) {
			getPlugin().getChatManager().colorMessage("");
			return;
		}

		User friend = getPlugin().getUserManager().getUser(Bukkit.getPlayerExact(args[0]));

		if (user.isFriendWith(friend)) {
			getPlugin().getChatManager().colorMessage("");
			return;
		}

		user.removeFriend(friend, true);

		getPlugin().getChatManager().colorMessage("");
	}

	@Override
	public List<String> getTutorial() {
		return Collections.singletonList("Ignore the specified player");
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