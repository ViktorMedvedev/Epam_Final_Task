package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.service.OfferService;
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

public class OfferListCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForUserSession(request)){
            return JspAddr.HOME_PAGE;
        }
        PaginationUtil<Offer> paginationUtil = new PaginationUtil<>();
        HttpSession session = request.getSession();
        ArrayList<Offer> offers;
        ArrayList<Offer> offersOnPage;
        OfferService offerService = new OfferService();
        try {
            offers = offerService.getOfferList();
            offersOnPage = paginationUtil.executePagination(request, offers);
            session.setAttribute(JspAttr.OFFERS, offersOnPage);
            return JspAddr.OFFER_TABLE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
