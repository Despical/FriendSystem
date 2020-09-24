package me.despical.friendsystem.user.option;

import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Preferences {

	private final Main plugin = JavaPlugin.getPlugin(Main.class);
	private final Map<Option, Boolean> options = new HashMap<>();
	private final User user;

	public Preferences(User user) {
		this.user = user;
		this.loadOptions();
	}

	public boolean getOption(Option option) {
		return options.get(option);
	}

	public User getUser() {
		return user;
	}

	private void loadOptions() {
		for (Option option : Option.values()) {
			options.put(option, plugin.getConfig().getBoolean(option.getPath(), option.getDefault()));
		}
	}

	public enum Option {
		;

		private final String path;
		private	final boolean def;

		Option(String path, boolean def) {
			this.path = path;
			this.def = def;
		}

		public String getPath() {
			return path;
		}

		public boolean getDefault() {
			return def;
		}
	}
}
