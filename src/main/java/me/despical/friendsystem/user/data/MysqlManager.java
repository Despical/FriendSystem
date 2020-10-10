package me.despical.friendsystem.user.data;

import me.despical.commonsbox.configuration.ConfigUtils;
import me.despical.commonsbox.database.MysqlDatabase;
import me.despical.friendsystem.Main;
import me.despical.friendsystem.user.User;
import me.despical.friendsystem.utils.Debugger;
import me.despical.friendsystem.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class MysqlManager implements Database {

	private final Main plugin;
	private User user;
	private final MysqlDatabase database;
	private final FileConfiguration config;

	public MysqlManager(Main plugin, User user) {
		this.plugin = plugin;
		this.user = user;
		this.database = plugin.getMysqlDatabase();
		this.config = ConfigUtils.getConfig(plugin, "friends");

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends_playerdata(uuid varchar(36) NOT NULL PRIMARY KEY, uuid2 varchar(36));");
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends_requests(uuid varchar(36), uuid2 varchar(36));");
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends_blocked(uuid varchar(36), uuid2 varchar(36));");
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends_frienddata(uuid varchar(36), uuid2 varchar(36));");
			} catch (SQLException e) {
				e.printStackTrace();
				MessageUtils.errorOccurred();
				Debugger.sendConsoleMessage("Cannot save contents to MySQL database!");
				Debugger.sendConsoleMessage("Check configuration of mysql.yml file or disable mysql option in config.yml");
			}
		});
	}

	@Override
	public void addFriend(User friend) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void acceptUser(User user) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void removeFriend(User friend, boolean ignored) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void denyUser(User user) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void toggleRequests() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void toggleNotifications() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String uuid = user.getPlayer().getUniqueId().toString();

			try (Connection connection = database.getConnection()) {
				Statement statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<String> getRequests() {
		return null;
	}

	@Override
	public boolean isFriendWith(User user) {
		return false;
	}

	@Override
	public boolean isIgnored(User user) {
		return false;
	}

	@Override
	public boolean isAcceptingRequests() {
		return false;
	}

	@Override
	public boolean isAcceptingNotifications() {
		return false;
	}

	@Override
	public boolean hasRequestFrom(User user) {
		return false;
	}

	public MysqlDatabase getDatabase() {
		return database;
	}
}