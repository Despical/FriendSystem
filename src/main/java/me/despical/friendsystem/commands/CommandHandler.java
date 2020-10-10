package me.despical.friendsystem.commands;

import me.despical.commonsbox.string.StringMatcher;
import me.despical.friendsystem.Main;
import me.despical.friendsystem.commands.admin.ReloadCommand;
import me.despical.friendsystem.commands.exception.CommandException;
import me.despical.friendsystem.commands.friend.*;
import me.despical.friendsystem.handlers.ChatManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class CommandHandler implements CommandExecutor {

	private final List<SubCommand> subCommands;
	private final Main plugin;

	public CommandHandler(Main plugin) {
		this.plugin = plugin;
		this.subCommands = new ArrayList<>();

		registerSubCommand(new AcceptRequestCommand());
		registerSubCommand(new AddFriendCommand());
		registerSubCommand(new DenyRequestCommand());
		registerSubCommand(new FriendsListCommand());
		registerSubCommand(new HelpCommand());
		registerSubCommand(new IgnoreUserCommand());
		registerSubCommand(new RemoveFriendCommand());
		registerSubCommand(new ToggleNotificationsCommand());
		registerSubCommand(new ToggleRequestsCommand());
		registerSubCommand(new ReloadCommand());

		plugin.getCommand("friends").setExecutor(this);
		plugin.getCommand("friends").setTabCompleter(new TabCompletion(this));
	}

	public void registerSubCommand(SubCommand subCommand) {
		subCommands.add(subCommand);
	}

	public List<SubCommand> getSubCommands() {
		return new ArrayList<>(subCommands);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			ChatManager chatManager = plugin.getChatManager();

			sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Header"));
			sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Description").replace("%cmd%", label));

			if (sender.hasPermission("friendsystem.admin")) {
				sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Admin-Bonus-Description").replace("%cmd%", label));
			}

			sender.sendMessage(chatManager.colorMessage("Commands.Help-Command.Footer"));
			return true;
		}

		for (SubCommand subCommand : subCommands) {
			if (subCommand.isValidTrigger(args[0])) {
				if (!subCommand.hasPermission(sender)) {
					sender.sendMessage(plugin.getChatManager().colorMessage("Commands.No-Permission"));
					return true;
				}

				if (subCommand.getSenderType() == SubCommand.SenderType.PLAYER && !(sender instanceof Player)) {
					sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Only-By-Player"));
					return false;
				}

				if (args.length - 1 >= subCommand.getMinimumArguments()) {
					try {
						subCommand.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
					} catch (CommandException e) {
						sender.sendMessage(ChatColor.RED + e.getMessage());
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + subCommand.getName() + " " + (subCommand.getPossibleArguments().length() > 0 ? subCommand.getPossibleArguments() : ""));
				}

				return true;
			}
		}

		List<StringMatcher.Match> matches = StringMatcher.match(args[0], subCommands.stream().map(SubCommand::getName).collect(Collectors.toList()));

		if (!matches.isEmpty()) {
			sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Did-You-Mean").replace("%command%", label + " " + matches.get(0).getMatch()));
			return true;
		}

		return true;
	}
}