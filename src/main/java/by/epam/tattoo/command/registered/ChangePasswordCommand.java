package main.java.by.epam.tattoo.command.registered;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForNullSession(request)){
            return JspAddr.LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        User userFromSession = (User)session.getAttribute(JspAttr.USER);
        String login = userFromSession.getLogin();
        String oldPassword = request.getParameter(JspParam.OLD_PASSWORD)
                .replace("<", "|").replace(">","|");
        String newPassword = request.getParameter(JspParam.NEW_PASSWORD)
                .replace("<", "|").replace(">","|");
        String confirmPassword= request.getParameter(JspParam.CONFIRM_PASSWORD)
                .replace("<", "|").replace(">","|");
        try {
            if (!newPassword.equals(confirmPassword)){
                request.setAttribute(JspAttr.WRONG_DATA, JspAttrVal.PASSWORD_DOES_NOT_MATCH);
                return JspAddr.CHANGE_PASSWORD;
            }
            UserService service = new UserService();
            User user = service.changePassword(login, oldPassword, newPassword);
            if (user != null) {
                session.setAttribute(JspAttr.USER, user);
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.CHANGED_PASSWORD);
                return JspAddr.MESSAGE_PAGE;
            }
            request.setAttribute(JspAttr.WRONG_DATA, JspAttrVal.WRONG_PASSWORD);
            return JspAddr.CHANGE_PASSWORD;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

