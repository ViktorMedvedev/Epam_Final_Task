package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.service.*;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OfferPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForUserSession(request)){
            return JspAddr.HOME_PAGE;
        }
        HttpSession session = request.getSession();
        session.removeAttribute(JspAttr.OFFER);
        String offerId = request.getParameter(JspParam.OFFER_ID);
        OfferService offerService = new OfferService();
        try {
            Offer offer = offerService.findOfferById(offerId);
            if (offer!= null){
                session.setAttribute(JspAttr.OFFER, offer);
                return JspAddr.OFFER_PAGE;
            }
            return JspAddr.HOME_PAGE;
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
