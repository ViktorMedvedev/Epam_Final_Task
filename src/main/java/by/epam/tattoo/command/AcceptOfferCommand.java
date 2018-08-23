package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.service.OfferService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AcceptOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
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
