package main.java.by.epam.tattoo.command.manager;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.service.OfferService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AcceptOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForNullSession(request)||AccessChecker.checkForUserSession(request)){
            return JspAddr.LOGIN_PAGE;
        }
        HttpSession session = request.getSession();
        OfferService offerService = new OfferService();
        String style = request.getParameter(JspParam.STYLE);
        String size = request.getParameter(JspParam.SIZE);
        String price = request.getParameter(JspParam.PRICE);
        Object obj = session.getAttribute(JspAttr.OFFER);
        Offer offer = (Offer) obj;
        try {
            if (offer != null) {
                if (offerService.acceptOffer(offer, style, size, price)) {
                    session.removeAttribute(JspAttr.OFFER);
                    request.setAttribute(JspAttr.MESSAGE, JspAttrVal.OFFER_ACCEPTED);
                    return JspAddr.MESSAGE_PAGE;
                }request.setAttribute(JspAttr.WRONG_DATA, "wrong");
                return JspAddr.OFFER_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }

}
