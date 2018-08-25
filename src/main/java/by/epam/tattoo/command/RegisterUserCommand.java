package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.*;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class RegisterUserCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(JspParam.EMAIL)
                .replace("<", "|").replace(">","|");
        String login = request.getParameter(JspParam.LOGIN)
                .replace("<", "|").replace(">","|");
        String password = request.getParameter(JspParam.PASSWORD)
                .replace("<", "|").replace(">","|");
        String confirmPassword = request.getParameter(JspParam.CONFIRM_PASSWORD)
                .replace("<", "|").replace(">","|");
        try {
            if (!password.equals(confirmPassword)){
                request.setAttribute(JspAttr.WRONG_DATA, JspAttrVal.PASSWORD_DOES_NOT_MATCH);
                return JspAddr.REGISTER_URL;
            }
            UserService service = new UserService();
            User user = service.registerUser(email, login, password);
            if (user != null) {
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.SIGNED_UP);
                return JspAddr.MESSAGE_PAGE;
            }
            request.setAttribute(JspAttr.DATA_EXISTS, JspAttrVal.USER_EXISTS);
            return JspAddr.REGISTER_URL;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

