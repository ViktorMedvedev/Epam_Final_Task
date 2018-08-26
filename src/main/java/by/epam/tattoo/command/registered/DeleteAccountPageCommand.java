package main.java.by.epam.tattoo.command.registered;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteAccountPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForNullSession(request)){
            return JspAddr.LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        String id = request.getParameter(JspParam.USER_ID);
        try {
            UserService userService = new UserService();
            User user = userService.findUserById(id);
            if (user != null) {
                session.setAttribute(JspAttr.USER_FROM_TABLE, user);
                return JspAddr.DELETE_ACCOUNT;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
