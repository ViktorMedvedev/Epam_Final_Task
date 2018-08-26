package main.java.by.epam.tattoo.command.registered;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteAccountCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForNullSession(request)) {
            return JspAddr.LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        UserService service = new UserService();
        Object obj = session.getAttribute(JspAttr.USER);
        User user = (User) obj;
        try {
            if (user.getRole()==Role.USER) {
                service.deleteUser(user);
                session.removeAttribute(JspAttr.USER);
                return JspAddr.LOGIN_PAGE;
            } else if (user.getRole() == Role.MODERATOR) {
                return JspAddr.HOME_PAGE;
            } else {
                User userFromTable = (User) session.getAttribute(JspAttr.USER_FROM_TABLE);
                service.deleteUser(userFromTable);
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.DELETE_USER_SUCCESS);
                return JspAddr.MESSAGE_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

