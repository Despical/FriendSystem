package me.despical.friendsystem.commands.friend;

import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.commonsbox.miscellaneous.MiscUtils;
import me.despical.commonsbox.number.NumberUtils;
import me.despical.friendsystem.commands.SubCommand;
import me.despical.friendsystem.user.User;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
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
	public void execute(CommandSender sender, String label, String[] args) {
		User user = plugin.getUserManager().getUser((Player) sender);
		FileConfiguration config = ConfigUtils.getConfig(plugin, "friends");
		List<String> friends = config.getConfigurationSection("").getKeys(false).stream().collect(Collectors.toList());
		int page = args.length == 1 ? NumberUtils.isInteger(args[0]) ? Integer.parseInt(args[0]) : 1 : 1;
		int pages = (friends.size() / 5) + (friends.size() % 5 == 0 ? 0 : 1);
		int max = pages * 5 * page;
		int min = max - 5;

		if (friends.isEmpty()) {
			sender.sendMessage(plugin.getChatManager().colorMessage("Commands.List-Command.No-Friends"));
			return;
		}

		sender.sendMessage(plugin.getChatManager().colorMessage("Commands.List-Command.Header"));
		MiscUtils.sendCenteredMessage(user.getPlayer(), plugin.getChatManager().colorMessage("Commands.List-Command.Page")
			.replace("%pages%", Integer.toString(pages)).replace("%page%", Integer.toString(page)));

		for(int i = min; i < max; i++) {
			try {
				sender.sendMessage(formatMessage(friends.get(i)));
			} catch (ArrayIndexOutOfBoundsException exception) {
				sender.sendMessage("");
			}
		}

		sender.sendMessage(plugin.getChatManager().colorMessage("Commands.List-Command.Footer"));
	}

	private String formatMessage(String name) {
		String message;

		if (Bukkit.getPlayer(UUID.fromString(name)) != null) {
			message = plugin.getChatManager().colorMessage("Commands.List-Command.Online");
		} else {
			message = plugin.getChatManager().colorMessage("Commands.List-Command.Offline");
		}

		message = StringUtils.replace(message, "%player%", name);
		return message;
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}
}