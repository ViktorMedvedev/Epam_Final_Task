package main.java.by.epam.tattoo.util;

import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AccessChecker {

    public static boolean checkForAdminSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(JspAttr.USER);
        User user = (User) obj;
        return user.getRole().equals(Role.ADMIN);
    }

    public static boolean checkForUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(JspAttr.USER);
        User user = (User) obj;
        return user.getRole().equals(Role.USER);
    }

    public static boolean checkForNullSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute(JspAttr.USER) == null;
    }
}
