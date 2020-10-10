package me.despical.friendsystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Despical
 * <p>
 * Created at 10.10.2020
 */
public class ConfigPreferences {

	private final Main plugin;
	private final Map<Option, Boolean> options = new HashMap<>();

	public ConfigPreferences(Main plugin) {
		this.plugin = plugin;

		loadOptions();
	}

	/**
	 * Returns whether option value is true or false
	 *
	 * @param option option to get value from
	 * @return true or false based on user configuration
	 */
	public boolean getOption(Option option) {
		return options.get(option);
	}

	private void loadOptions() {
		Arrays.stream(Option.values()).forEach(option -> options.put(option, plugin.getConfig().getBoolean(option.getPath(), option.getDefault())));
	}

	public enum Option {
		DATABASE_ENABLED("DatabaseActivated", false);

		private final String path;
		private final boolean def;

		Option(String path, boolean def) {
			this.path = path;
			this.def = def;
		}

		public String getPath() {
			return path;
		}

		/**
		 * @return default value of option if absent in config
		 */
		public boolean getDefault() {
			return def;
		}
	}
}