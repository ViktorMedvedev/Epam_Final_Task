package main.java.by.epam.tattoo.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public Command defineCommand(String commandName) throws CommandException {
        CommandType type = CommandType.getCommandType(commandName);
        if (type != null) {
            return type.getCurrentCommand();
        } else {
            throw new CommandException();
        }
    }
}

