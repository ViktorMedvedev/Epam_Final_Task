package main.java.by.epam.tattoo.command.common;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class TattooPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttr.ORDER);
        session.removeAttribute(JspAttr.HAS_ALREADY_RATED);
        String id = request.getParameter(JspParam.TATTOO_ID);
        TattooService tattooService = new TattooService();
        Object userObj = session.getAttribute(JspAttr.USER);
        User user = (User) userObj;
        OrderService orderService = new OrderService();
        try {
            Tattoo tattoo = tattooService.findTattooById(id);
            if (tattoo != null && user != null) {
                if (tattooService.hasAlreadyRated(tattoo, user)) {
                    session.setAttribute(JspAttr.HAS_ALREADY_RATED, JspAttrVal.HAS_RATED);
                }
                Order order = orderService.findOrderByUserIdAndTattooId(user.getId(), tattoo.getId());
                if (user.getRole() != Role.ADMIN && order != null) {
                    session.setAttribute(JspAttr.ORDER, order);
                }
            }session.setAttribute(JspAttr.TATTOO, tattoo);
            return JspAddr.TATTOO_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
