package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.tattoo.Tattoo;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class OrderCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = request.getParameter(JspParam.TATTOO_ID);
        try {
            TattooService service = new TattooService();
            Tattoo tattoo = service.findTattooById(id);
            if (tattoo != null) {
                session.setAttribute(JspAttr.TATTOO, tattoo);
                return JspAddr.ORDER_PAGE;
            }
            session.setAttribute(JspAttr.WRONG_DATA, "wrong");
            return JspAddr.HOME_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
