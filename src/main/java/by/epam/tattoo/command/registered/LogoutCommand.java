package main.java.by.epam.tattoo.command.registered;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttr.USER);
        return JspAddr.LOGIN_PAGE;
    }
}

