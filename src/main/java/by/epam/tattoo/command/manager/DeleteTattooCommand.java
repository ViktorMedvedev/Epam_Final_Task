package main.java.by.epam.tattoo.command.manager;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteTattooCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForNullSession(request)||AccessChecker.checkForUserSession(request)){
            return JspAddr.LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        TattooService tattooService = new TattooService();
        Object obj = session.getAttribute(JspAttr.TATTOO);
        Tattoo tattoo = (Tattoo) obj;
        try {
            if (tattoo != null) {
                tattooService.deleteTattoo(tattoo);
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.TATTOO_DELETED);
                return JspAddr.MESSAGE_PAGE;
            }return JspAddr.TATTOO_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

}
