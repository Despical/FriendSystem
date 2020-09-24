package me.despical.friendsystem.commands;

import me.despical.friendsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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



		plugin.getCommand("").setExecutor(this);
		plugin.getCommand("").setTabCompleter(new TabCompletion(this));
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
			sender.sendMessage(ChatColor.DARK_AQUA + "This server is running " + ChatColor.AQUA + "TNT Run " + ChatColor.DARK_AQUA + "v" + this.plugin.getDescription().getVersion() + " by " + ChatColor.AQUA + "Despical");
			if (sender.hasPermission("tntrun.admin")) {
				sender.sendMessage(ChatColor.DARK_AQUA + "Commands: " + ChatColor.AQUA + "/" + label + " help");
			}

			return true;
		}

		for (SubCommand subCommand : subCommands) {
			if (subCommand.isValidTrigger(args[0])) {
				if (!subCommand.hasPermission(sender)) {
//					sender.sendMessage(plugin.getChatManager().getPrefix() + plugin.getChatManager().colorMessage("Commands.No-Permission"));
					return true;
				}

//				if (subCommand.getSenderType() == SenderType.PLAYER && !(sender instanceof Player)) {
//					sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Only-By-Player"));
//					return false;
//				}

//				if (args.length - 1 >= subCommand.getMinimumArguments()) {
//					try {
//						subCommand.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
//					} catch (CommandException e) {
//						sender.sendMessage(ChatColor.RED + e.getMessage());
//					}
//				} else {
//					if (subCommand.getType() == SubCommand.CommandType.GENERIC) {
//						sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + subCommand.getName() + " " + (subCommand.getPossibleArguments().length() > 0 ? subCommand.getPossibleArguments() : ""));
//					}
//				}
//
//				return true;
			}
		}
//
//		List<StringMatcher.Match> matches = StringMatcher.match(args[0], subCommands.stream().map(SubCommand::getName).collect(Collectors.toList()));
//
//		if (!matches.isEmpty()) {
//			sender.sendMessage(plugin.getChatManager().colorMessage("Commands.Did-You-Mean").replace("%command%", label + " " + matches.get(0).getMatch()));
//			return true;
//		}

		return true;
	}
}