package me.despical.friendsystem.commands.exception;

/**
 * @author Despical
 * <p>
 * Created at 22.09.2020
 */
public class CommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public CommandException(String message) {
		super(message);
	}
}