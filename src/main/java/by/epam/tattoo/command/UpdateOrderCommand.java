package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String status = request.getParameter(JspParam.STATUS);
        OrderService orderService = new OrderService();
        Object obj = session.getAttribute(JspAttr.ORDER);
        Order order = (Order) obj;
        try {
            if (order != null) {
                orderService.updateOrder(status, order.getOrderId());
                session.setAttribute(JspAttr.ORDER, orderService.findOrderById(order.getOrderId()));
                return JspAddr.ORDER_DECISION_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
