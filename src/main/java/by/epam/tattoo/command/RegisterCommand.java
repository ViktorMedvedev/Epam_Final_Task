package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import main.java.by.epam.tattoo.entity.user.Role;
import main.java.by.epam.tattoo.entity.user.User;
import main.java.by.epam.tattoo.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class RegisterCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter(JspParam.EMAIL);
        String login = request.getParameter(JspParam.LOGIN);
        String password = request.getParameter(JspParam.PASSWORD);
        Role role = Role.USER;
        try {
            UserService service = new UserService();
            User user = service.registerUser(email, login, password, role);
            if (user != null) {
                session.setAttribute(JspAttr.REGISTERED, "registered");
                return JspAddr.MESSAGE_PAGE;
            }
            session.setAttribute(JspAttr.DATA_EXISTS, "exists");
            return JspAddr.REGISTER_URL;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

