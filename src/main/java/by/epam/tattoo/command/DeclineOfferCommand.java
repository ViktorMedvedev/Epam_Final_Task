package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.service.OfferService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeclineOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForUserSession(request)){
            return JspAddr.HOME_PAGE;
        }
        HttpSession session = request.getSession();
        OfferService offerService = new OfferService();
        Object obj = session.getAttribute(JspAttr.OFFER);
        Offer offer = (Offer) obj;
        try {
            if (offer != null) {
                offerService.declineOffer(offer);
                session.removeAttribute(JspAttr.OFFER);
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.OFFER_DECLINED);
                return JspAddr.MESSAGE_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
