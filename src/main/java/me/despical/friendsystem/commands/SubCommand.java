package me.despical.friendsystem.commands;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.commands.exception.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public abstract class SubCommand {

	protected final Main plugin = JavaPlugin.getPlugin(Main.class);
	private final String name;
	private String permission;
	private final String[] aliases;

	public SubCommand(String name) {
		this(name, new String[0]);
	}

	public SubCommand(String name, String... aliases) {
		this.name = name;
		this.aliases = aliases;
	}

	public String getName() {
		return name;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public final boolean hasPermission(CommandSender sender) {
		if (permission == null) return true;
		return sender.hasPermission(permission);
	}

	public abstract String getPossibleArguments();

	public abstract int getMinimumArguments();

	public abstract void execute(CommandSender sender, String label, String[] args) throws CommandException;

	public abstract SenderType getSenderType();

	public enum SenderType {
		PLAYER, BOTH
	}

	public final boolean isValidTrigger(String name) {
		if (this.name.equalsIgnoreCase(name)) {
			return true;
		}

		if (aliases != null) {
			for (String alias : aliases) {
				if (alias.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}

		return false;
	}
}