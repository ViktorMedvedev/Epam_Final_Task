package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.util.JspAddr;

import javax.servlet.http.HttpServletRequest;

public class ErrorPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return JspAddr.ERROR_PAGE;
    }
}
