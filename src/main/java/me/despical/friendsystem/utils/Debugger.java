package me.despical.friendsystem.utils;

import me.despical.commonsbox.compat.VersionResolver;
import me.despical.commonsbox.string.StringMatcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Despical
 * <p>
 * Created at 21.09.2020
 */
public class Debugger {

	private static boolean enabled = false;
	private static final Logger logger = Logger.getLogger("Friend System");

	private Debugger() {}

	public static void setEnabled(boolean enabled) {
		Debugger.enabled = enabled;
	}

	public static void sendConsoleMessage(String message) {
		if (message.contains("#") && VersionResolver.isCurrentEqualOrHigher(VersionResolver.ServerVersion.v1_16_R1)) {
			message = StringMatcher.matchColorRegex(message);
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	/**
	 * Prints debug message with selected log level. Messages of level INFO or TASK
	 * won't be posted if debugger is enabled, warnings and errors will be.
	 *
	 * @param level level of debugged message
	 * @param msg message to debug
	 */
	public static void debug(Level level, String msg) {
		if (!enabled && (level != Level.WARNING || level != Level.SEVERE)) {
			return;
		}

		logger.log(level, "[FSDBG] " + msg);
	}

	/**
	 * Prints debug message with selected INFO log level.
	 *
	 * @param msg debugged message
	 * @param params to debug
	 */
	public static void debug(String msg, Object... params) {
		debug(Level.INFO, "[FSDBG] " + msg, params);
	}

	/**
	 * Prints debug message with selected log level. Messages of level INFO or TASK
	 * won't be posted if debugger is enabled, warnings and errors will be.
	 *
	 * @param msg debugged message
	 */
	public static void debug(String msg) {
		debug(Level.INFO, msg);
	}

	/**
	 * Prints debug message with selected log level and replaces parameters.
	 * Messages of level INFO or TASK won't be posted if debugger is enabled,
	 * warnings and errors will be.
	 *
	 * @param level level of debugged message
	 * @param msg debugged message
	 * @param params any params to debug
	 */
	public static void debug(Level level, String msg, Object... params) {
		if (!enabled && (level != Level.WARNING || level != Level.FINE)) {
			return;
		}

		logger.log(level, "[FSDBG] " + msg, params);
	}
}