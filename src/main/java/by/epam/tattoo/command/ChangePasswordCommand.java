package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(JspParam.LOGIN);
        String oldPassword = request.getParameter(JspParam.OLD_PASSWORD);
        String newPassword = request.getParameter(JspParam.NEW_PASSWORD);
        String confirmPassword= request.getParameter(JspParam.CONFIRM_PASSWORD);
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

