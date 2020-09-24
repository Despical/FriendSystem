package me.despical.friendsystem;

import me.despical.commonsbox.compat.VersionResolver;
import me.despical.friendsystem.handlers.ChatManager;
import me.despical.friendsystem.user.UserManager;
import me.despical.friendsystem.utils.Debugger;
import me.despical.friendsystem.utils.ExceptionLogHandler;
import me.despical.friendsystem.utils.MessageUtils;
import me.despical.friendsystem.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

public class Main extends JavaPlugin {

	private ExceptionLogHandler exceptionLogHandler;
	private boolean forceDisable = false;
	private UserManager userManager;
	private ChatManager chatManager;

	@Override
	public void onEnable() {
		if (!validateIfPluginShouldStart()) {
			return;
		}

		exceptionLogHandler = new ExceptionLogHandler(this);
		saveDefaultConfig();

		Debugger.setEnabled(getDescription().getVersion().contains("d") || getConfig().getBoolean("Debug-Messages", false));
		Debugger.debug("Initialization start");

		long start = System.currentTimeMillis();

		setupFiles();
		initializeClasses();
		checkUpdate();

		Debugger.debug(Level.INFO, "Initialization finished took {0} ms", System.currentTimeMillis() - start);
	}

	private boolean validateIfPluginShouldStart() {
		if (!VersionResolver.isAllSupported()) {
			MessageUtils.thisVersionIsNotSupported();
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Your server version is not supported by Friend System!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Sadly, we must shut off. Maybe you consider changing your server version?");
			forceDisable = true;
			getServer().getPluginManager().disablePlugin(this);
			return false;

		} try {
			Class.forName("org.spigotmc.SpigotConfig");

		} catch (Exception e) {
			MessageUtils.thisVersionIsNotSupported();
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Your server software is not supported by Friend System!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "We support only Spigot and Spigot forks only! Shutting off...");
			forceDisable = true;
			getServer().getPluginManager().disablePlugin(this);
			return false;
		}

		return true;
	}


	@Override
	public void onDisable() {

	}

	private void initializeClasses() {
		userManager = new UserManager();
		chatManager = new ChatManager(this);

		registerSoftDependenciesAndServices();
	}

	private void registerSoftDependenciesAndServices() {
		Debugger.debug("Hooking into soft dependencies");
		long start = System.currentTimeMillis();

		startPluginMetrics();

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Debugger.debug("Hooking into PlaceholderAPI");
//			new PlaceholderManager().register();
		}

		Debugger.debug(Level.INFO, "Hooked into soft dependencies took {0} ms", System.currentTimeMillis() - start);
	}

	private void startPluginMetrics() {
		Metrics metrics = new Metrics(this, 0);

		metrics.addCustomChart(new Metrics.SimplePie("database_enabled", () -> String.valueOf(getConfig().getBoolean("DatabaseActivated", false))));
		metrics.addCustomChart(new Metrics.SimplePie("update_notifier", () -> {
			if (getConfig().getBoolean("Update-Notifier.Enabled", true)) {
				if (getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true)) {
					return "Enabled with beta notifier";
				} else {
					return "Enabled";
				}
			} else {
				if (getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true)) {
					return "Beta notifier only";
				} else {
					return "Disabled";
				}
			}
		}));
	}

	private void checkUpdate() {
		if (!getConfig().getBoolean("Update-Notifier.Enabled", true)) {
			return;
		}

		UpdateChecker.init(this, 1).requestUpdateCheck().whenComplete((result, exception) -> {
			if (!result.requiresUpdate()) {
				return;
			}

			if (result.getNewestVersion().contains("b")) {
				if (getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true)) {
					Bukkit.getConsoleSender().sendMessage("[FriendSystem] Found a new beta version available: v" + result.getNewestVersion());
					Bukkit.getConsoleSender().sendMessage("[FriendSystem] Download it on SpigotMC:");
				}
				return;
			}

			MessageUtils.updateIsHere();
			Bukkit.getConsoleSender().sendMessage("[FriendSystem] Found a new version available: v" + result.getNewestVersion());
			Bukkit.getConsoleSender().sendMessage("[FriendSystem] Download it SpigotMC:");
		});
	}

	private void setupFiles() {
		for (String fileName : Arrays.asList("mysql", "messages", "friends")) {
			File file = new File(getDataFolder() + File.separator + fileName + ".yml");

			if (!file.exists()) {
				saveResource(fileName + ".yml", false);
			}
		}
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}
}
