package main.java.by.epam.tattoo.command;

public enum CommandType {
    REGISTER(new RegisterCommand()),

    LOGIN(new LoginCommand()),

    LOGOUT(new LogoutCommand()),

    LOCALE(new ChangeLocaleCommand()),

    HOME(new MainPageCommand()),

    ORDER(new OrderCommand()),

    CHANGE_PASSWORD(new ChangePasswordCommand()),

    REGISTER_ORDER(new RegisterOrderCommand()),

    ORDER_LIST(new OrderListCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public static CommandType getCommandType(String commandName) {
        return CommandType.valueOf(commandName.toUpperCase().replace("-", "_"));
    }
}
