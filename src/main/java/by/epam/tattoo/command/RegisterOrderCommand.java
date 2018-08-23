package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegisterOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute(JspAttr.USER);
        Object tattooObj = session.getAttribute(JspAttr.TATTOO);
        User user = (User) userObj;
        Tattoo tattoo = (Tattoo)tattooObj;
        int userId = user.getId();
        int tattooId = tattoo.getId();
        OrderService service = new OrderService();
        try {
            Order order = service.registerOrder(userId, tattooId);
            if (order != null) {
                session.setAttribute(JspAttr.ORDER, order);
                return JspAddr.TATTOO_PAGE;
            }
            request.setAttribute(JspAttr.MESSAGE, JspAttrVal.ORDER_FAIL);
            return JspAddr.MESSAGE_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            e.printStackTrace();
        }
        return null;
    }
}
