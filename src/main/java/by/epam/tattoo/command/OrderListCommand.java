package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.order.Order;
import main.java.by.epam.tattoo.entity.tattoo.Tattoo;
import main.java.by.epam.tattoo.entity.user.Role;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class OrderListCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userRole = request.getParameter("role");
        ArrayList<Order> orders;
        try {
            OrderService orderService = new OrderService();
            if (userRole.equals(Role.ADMIN.toString())) {
                orders = orderService.findAllOrders();
            }else {
                orders = orderService.findUserOrders(request.getParameter("userId"));
            }
            if (!orders.isEmpty()) {
                session.setAttribute(JspAttr.ORDERS, orders);
                return JspAddr.ORDER_TABLE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
