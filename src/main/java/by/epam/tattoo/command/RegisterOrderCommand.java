package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.order.Order;
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

public class RegisterOrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = request.getParameter(JspParam.USER_ID);
        String tattooId = request.getParameter(JspParam.TATTOO_ID);
        try {
            OrderService service = new OrderService();
            Order order = service.registerOrder(userId, tattooId);
            if (order != null) {
                session.setAttribute(JspAttr.ORDER, "success");
                return JspAddr.MESSAGE_PAGE;
            }
            session.setAttribute(JspAttr.ORDER, "fail");
            return JspAddr.MESSAGE_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            e.printStackTrace();
        }
        return null;
    }
}
