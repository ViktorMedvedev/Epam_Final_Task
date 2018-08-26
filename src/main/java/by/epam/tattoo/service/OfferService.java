package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.impl.OfferDaoImpl;
import main.java.by.epam.tattoo.entity.Offer;

import java.util.ArrayList;

public class OfferService {
    private OfferDaoImpl dao = new OfferDaoImpl();

    public Offer registerOffer(int userId, byte[] image) throws ServiceException {
        Offer offer = new Offer(userId, image);
        try {
            return dao.register(offer);
        } catch (DaoException e) {
            throw new ServiceException("Fail", e);
        }
    }

    public boolean acceptOffer(Offer offer, String style, String size, String price) throws ServiceException {
        if (style.isEmpty()||size.isEmpty()||price.isEmpty()){
            return false;
        }
        TattooService tattooService = new TattooService();
        UserService userService = new UserService();
        try {
            tattooService.registerTattoo(style, size, price, offer.getImageBytes());
            userService.updateUserDiscount(offer.getUserId());
            return dao.delete(offer);
        } catch (DaoException e) {
            throw new ServiceException("Fail", e);
        }
    }

    public boolean declineOffer(Offer offer) throws ServiceException {
        try {
            return dao.delete(offer);
        } catch (DaoException e) {
            throw new ServiceException("Fail", e);
        }
    }

    boolean deleteUserOffers(int userId) throws ServiceException {
        try {
            return dao.deleteAllUserOffers(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Offer> getOfferList() throws ServiceException {
        try {
            return dao.findAllOffers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public byte[] findTattooImage(String id) throws ServiceException {
        try {
            return dao.findTattooImageById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Offer findOfferById(String id) throws ServiceException {
        try {
            return dao.findOfferById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
