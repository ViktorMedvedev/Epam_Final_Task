package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(JspParam.LOGIN)
                .replace("<", "").replace(">","");
        String password = request.getParameter(JspParam.PASSWORD)
                .replace("<", "").replace(">","");
        try {
            UserService service = new UserService();
            User user = service.loginUser(login, password);
            if (user != null) {
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.SIGNED_IN);
                session.setAttribute(JspAttr.USER, user);
                return JspAddr.MESSAGE_PAGE;
            }
            request.setAttribute(JspAttr.WRONG_DATA, "wrong");
            return JspAddr.LOGIN_PAGE;

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

