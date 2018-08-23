package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class ChangeUserRoleCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        ArrayList<User> users;
        String id = request.getParameter(JspParam.USER_ID);
        try {
            userService.updateUserRole(id);
            users = userService.findAllUsers();
            session.setAttribute(JspAttr.USERS, users);
            return JspAddr.USER_TABLE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
