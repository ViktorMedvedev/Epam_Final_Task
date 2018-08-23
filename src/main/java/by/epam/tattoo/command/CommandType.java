package main.java.by.epam.tattoo.command;

public enum CommandType {
    REGISTER(new RegisterUserCommand()),

    LOGIN(new LoginCommand()),

    LOGOUT(new LogoutCommand()),

    LOCALE(new ChangeLocaleCommand()),

    HOME(new MainPageCommand()),

    TATTOO_PAGE(new TattooPageCommand()),

    CHANGE_PASSWORD(new ChangePasswordCommand()),

    REGISTER_ORDER(new RegisterOrderCommand()),

    ORDER_LIST(new OrderListCommand()),

    USER_LIST(new UserListCommand()),

    DELETE_ACCOUNT(new DeleteAccountCommand()),

    OFFER_LIST(new OfferListCommand()),

    CANCEL_ORDER(new DeleteOrderCommand()),

    ERROR_PAGE(new ErrorPageCommand()),

    DELETE_TATTOO(new DeleteTattooCommand()),

    DELETE_TATTOO_PAGE(new DeleteTattooPageCommand()),

    ORDER_DECISION_PAGE(new OrderDecisionPageCommand()),

    UPDATE_ORDER(new UpdateOrderCommand()),

    UPDATE_RATING(new RateTattooCommand()),

    OFFER_PAGE(new OfferPageCommand()),

    ACCEPT_OFFER(new AcceptOfferCommand()),

    CHANGE_USER_ROLE(new ChangeUserRoleCommand()),

    DECLINE_OFFER(new DeclineOfferCommand()),

    DELETE_ACCOUNT_PAGE(new DeleteAccountPageCommand());

    private Command command;


    CommandType(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }


    public static CommandType getCommandType(String commandName) {
        if (commandName!=null) {
            return CommandType.valueOf(commandName.toUpperCase().replace("-", "_"));
        }return ERROR_PAGE;
    }
}