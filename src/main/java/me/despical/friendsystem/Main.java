package me.despical.friendsystem;

import me.despical.commonsbox.compat.VersionResolver;
import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.commonsbox.database.MysqlDatabase;
import me.despical.friendsystem.commands.CommandHandler;
import me.despical.friendsystem.events.JoinEvent;
import me.despical.friendsystem.events.QuitEvent;
import me.despical.friendsystem.handlers.ChatManager;
import me.despical.friendsystem.handlers.PlaceholderManager;
import me.despical.friendsystem.user.UserManager;
import me.despical.friendsystem.utils.Debugger;
import me.despical.friendsystem.utils.ExceptionLogHandler;
import me.despical.friendsystem.utils.MessageUtils;
import me.despical.friendsystem.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class Main extends JavaPlugin {

	private ExceptionLogHandler exceptionLogHandler;
	private boolean forceDisable = false;
	private ConfigPreferences configPreferences;
	private MysqlDatabase database;
	private UserManager userManager;
	private ChatManager chatManager;
	private CommandHandler commandHandler;

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
		configPreferences = new ConfigPreferences(this);

		setupFiles();
		initializeClasses();
		checkUpdate();

		Debugger.debug("Initialization finished took {0} ms", System.currentTimeMillis() - start);
	}

	private boolean validateIfPluginShouldStart() {
		if (VersionResolver.isCurrentLower(VersionResolver.ServerVersion.v1_8_R1)) {
			MessageUtils.thisVersionIsNotSupported();
			Debugger.sendConsoleMessage("&cYour server version is not supported by Friend System!");
			Debugger.sendConsoleMessage("&cSadly, we must shut off. Maybe you consider changing your server version?");
			forceDisable = true;
			getServer().getPluginManager().disablePlugin(this);
			return false;
		} try {
			Class.forName("org.spigotmc.SpigotConfig");
		} catch (Exception e) {
			MessageUtils.thisVersionIsNotSupported();
			Debugger.sendConsoleMessage("&cYour server software is not supported by Friend System!");
			Debugger.sendConsoleMessage("&cWe support only Spigot and Spigot forks only! Shutting off...");
			forceDisable = true;
			getServer().getPluginManager().disablePlugin(this);
			return false;
		}

		return true;
	}

	@Override
	public void onDisable() {
		if (forceDisable) {
			return;
		}

		Debugger.debug("System disable initialized");
		long start = System.currentTimeMillis();

		Bukkit.getLogger().removeHandler(exceptionLogHandler);

		if (configPreferences.getOption(ConfigPreferences.Option.DATABASE_ENABLED)) {
			getMysqlDatabase().shutdownConnPool();
		}

		Debugger.debug("System disable finished took {0} ms", System.currentTimeMillis() - start);
	}

	private void initializeClasses() {
		userManager = new UserManager();
		chatManager = new ChatManager(this);
		commandHandler = new CommandHandler(this);

		if (configPreferences.getOption(ConfigPreferences.Option.DATABASE_ENABLED)) {
			FileConfiguration config = ConfigUtils.getConfig(this, "mysql");
			database = new MysqlDatabase(config.getString("user"), config.getString("password"), config.getString("address"));
		}

		registerSoftDependenciesAndServices();

		new JoinEvent(this);
		new QuitEvent(this);
	}

	private void registerSoftDependenciesAndServices() {
		Debugger.debug("Hooking into soft dependencies");
		long start = System.currentTimeMillis();

		startPluginMetrics();

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Debugger.debug("Hooking into PlaceholderAPI");
			new PlaceholderManager().register();
		}

		Debugger.debug("Hooked into soft dependencies took {0} ms", System.currentTimeMillis() - start);
	}

	private void startPluginMetrics() {
		Metrics metrics = new Metrics(this, 9069);

		if (!metrics.isEnabled()) {
			return;
		}

		metrics.addCustomChart(new Metrics.SimplePie("database_enabled", () -> String.valueOf(configPreferences.getOption(ConfigPreferences.Option.DATABASE_ENABLED))));
		metrics.addCustomChart(new Metrics.SimplePie("update_notifier", () -> {
			if (getConfig().getBoolean("Update-Notifier.Enabled", true)) {
				return getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true) ? "Enabled with beta notifier" : "Enabled";
			}

			return getConfig().getBoolean("Update-Notifier.Notify-Beta-Versions", true) ? "Beta notifier only" : "Disabled";
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
					Debugger.sendConsoleMessage("[FriendSystem] Found a new beta version available: v" + result.getNewestVersion());
					Debugger.sendConsoleMessage("[FriendSystem] Download it on SpigotMC:");
				}
				return;
			}

			MessageUtils.updateIsHere();
			Debugger.sendConsoleMessage("[FriendSystem] Found a new version available: v" + result.getNewestVersion());
			Debugger.sendConsoleMessage("[FriendSystem] Download it SpigotMC:");
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

	public ConfigPreferences getConfigPreferences() {
		return configPreferences;
	}

	public MysqlDatabase getMysqlDatabase() {
		return database;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
}