package me.despical.friendsystem.utils;

import me.despical.friendsystem.Main;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Despical
 * <p>
 * Created at 21.09.2020
 */
public class ExceptionLogHandler extends Handler {

	private final Main plugin;

	public ExceptionLogHandler(Main plugin) {
		this.plugin = plugin;

		Bukkit.getLogger().addHandler(this);
	}

	@Override
	public void close() throws SecurityException {}

	@Override
	public void flush() {}

	@Override
	public void publish(LogRecord record) {
		Throwable throwable = record.getThrown();

		if (!(throwable instanceof Exception) || !throwable.getClass().getSimpleName().contains("Exception")) {
			return;
		}

		if (throwable.getStackTrace().length <= 0) {
			return;
		}

		if (throwable.getCause() != null && throwable.getCause().getStackTrace() != null) {
			if (!throwable.getCause().getStackTrace()[0].getClassName().contains("me.despical.friendsystem")) {
				return;
			}
		}

		if (!throwable.getStackTrace()[0].getClassName().contains("me.despical.friendsystem")) {
			return;
		}

		if (containsBlacklistedClass(throwable)) {
			return;
		}

		record.setThrown(null);

		Exception exception = throwable.getCause() != null ? (Exception) throwable.getCause() : (Exception) throwable;
		StringBuilder stacktrace = new StringBuilder(exception.getClass().getSimpleName());

		if (exception.getMessage() != null) {
			stacktrace.append(" (").append(exception.getMessage()).append(")");
		}

		stacktrace.append("\n");

		Arrays.stream(exception.getStackTrace()).forEach(str -> stacktrace.append(str.toString()).append("\n"));

		plugin.getLogger().log(Level.WARNING, "[Reporter service] <<-----------------------------[START]----------------------------->>");
		plugin.getLogger().log(Level.WARNING, stacktrace.toString());
		plugin.getLogger().log(Level.WARNING, "[Reporter service] <<------------------------------[END]------------------------------>>");

		record.setMessage("[FriendSystem] We have found a bug in the code. Contact us at our official discord server (Invite link: https://discordapp.com/invite/Vhyy4HA) with the following error given above!");
	}

	private boolean containsBlacklistedClass(Throwable throwable) {
		for (StackTraceElement element : throwable.getStackTrace()) {
			for (String blacklist : Arrays.asList("me.despical.friendsystem.user.data.MysqlManager", "me.despical.friendsystem.commonsbox.database.MysqlDatabase")) {
				if (element.getClassName().contains(blacklist)) {
					return true;
				}
			}
		}

		return false;
	}
}