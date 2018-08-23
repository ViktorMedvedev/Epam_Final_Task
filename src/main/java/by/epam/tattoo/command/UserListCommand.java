package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.PaginationUtil;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.UserService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class UserListCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        PaginationUtil<User> paginationUtil = new PaginationUtil<>();
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttr.USERS);
        UserService userService = new UserService();
        ArrayList<User> users;
        ArrayList<User> usersOnPage;
        try {
            users = userService.findAllUsers();
            usersOnPage = paginationUtil.executePagination(request, users);
            session.setAttribute(JspAttr.USERS, usersOnPage);
            return JspAddr.USER_TABLE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}

