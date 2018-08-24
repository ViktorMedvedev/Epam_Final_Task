package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
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

public class OrderDecisionPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForUserSession(request)){
            return JspAddr.HOME_PAGE;
        }
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttr.ORDER);
        String tattooId = request.getParameter(JspParam.TATTOO_ID);
        String userId = request.getParameter(JspParam.USER_ID);
        TattooService tattooService = new TattooService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        try {
            User user = userService.findUserById(userId);
            Tattoo tattoo = tattooService.findTattooById(tattooId);
            if (tattoo != null && user!= null){
                Order order = orderService.findOrderByUserIdAndTattooId(user.getId(), tattoo.getId());
                if (order != null) {
                    session.setAttribute(JspAttr.ORDER, order);
                }
                session.setAttribute(JspAttr.TATTOO, tattoo);
                session.setAttribute(JspAttr.USER_FROM_TABLE, user);
                return JspAddr.ORDER_DECISION_PAGE;
            }
            return JspAddr.HOME_PAGE;
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
