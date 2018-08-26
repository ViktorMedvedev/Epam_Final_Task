package main.java.by.epam.tattoo.command.registered;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.OrderService;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.PaginationUtil;
import main.java.by.epam.tattoo.service.ServiceException;
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
        if (AccessChecker.checkForNullSession(request)){
            return JspAddr.LOGIN_PAGE;
        }
        PaginationUtil<Order> paginationUtil = new PaginationUtil<>();
        HttpSession session = request.getSession();
        ArrayList<Order> orders;
        ArrayList<Order> ordersOnPage;
        Object obj = session.getAttribute(JspAttr.USER);
        User user = (User) obj;
        OrderService orderService = new OrderService();
        try {
            orders = orderService.getOrderList(user);
            ordersOnPage = paginationUtil.executePagination(request, orders);
            session.setAttribute(JspAttr.ORDERS, ordersOnPage);
            return JspAddr.ORDER_TABLE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
