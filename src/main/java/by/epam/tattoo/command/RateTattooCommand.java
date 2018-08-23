package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RateTattooCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String rating = request.getParameter(JspParam.RATING);
        TattooService tattooService = new TattooService();
        Object obj = session.getAttribute(JspAttr.TATTOO);
        Tattoo tattoo = (Tattoo) obj;
        Object userObj = session.getAttribute(JspAttr.USER);
        User user = (User) userObj;
        try {
            if (tattoo != null) {
                if (tattooService.updateTattooRating(tattoo, user, rating)) {
                    session.setAttribute(JspAttr.TATTOO, tattooService.findTattooById(tattoo.getId()));
                    session.setAttribute(JspAttr.HAS_ALREADY_RATED, JspAttrVal.HAS_RATED);
                    return JspAddr.TATTOO_PAGE;
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
