package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Order;
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

public class DeleteOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        OrderService orderService = new OrderService();
        Object obj = session.getAttribute(JspAttr.ORDER);
        Order order = (Order) obj;
        try {
            if (order != null) {
                if (orderService.deleteOrder(order)) {
                    session.removeAttribute(JspAttr.ORDER);
                    return JspAddr.TATTOO_PAGE;
                }else{
                    request.setAttribute(JspAttr.MESSAGE, JspAttrVal.DELETE_ORDER_FAIL);
                    return JspAddr.MESSAGE_PAGE;
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

}
