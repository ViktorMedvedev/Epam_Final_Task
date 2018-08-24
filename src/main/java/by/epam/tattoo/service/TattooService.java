package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.tattoo.TattooDao;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.util.ConstantHeap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TattooService {
    private TattooDao dao = new TattooDao();

    public Tattoo registerTattoo(String style, String size, String price, byte[] image) throws ServiceException {
        if (style.isEmpty() || size.isEmpty() || price.isEmpty()) {
            return null;
        }
        Tattoo tattoo = new Tattoo(style, size, new BigDecimal(price), image);
        try {
            return dao.register(tattoo);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public ArrayList<Tattoo> findTattoosByParameter(String style, String size) throws ServiceException {
        ArrayList<Tattoo> tattoos;
        try {
            if (ConstantHeap.NOT_CHOSEN_EN.equals(style)
                    || ConstantHeap.NOT_CHOSEN_RU.equals(style)
                    || style == null) {
                if (ConstantHeap.NOT_CHOSEN_EN.equals(size)
                        || ConstantHeap.NOT_CHOSEN_RU.equals(size)
                        || size == null) {
                    tattoos = dao.findAllTattoos();
                } else {
                    tattoos = dao.findTattoosBySize(size);
                }
            } else {
                if (ConstantHeap.NOT_CHOSEN_EN.equals(size)
                        || ConstantHeap.NOT_CHOSEN_RU.equals(size)
                        || size == null) {
                    tattoos = dao.findTattoosByStyle(style);
                } else {
                    tattoos = dao.findTattoosByStyleAndSize(style, size);
                }
            }
            return tattoos;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Tattoo findTattooById(String id) throws ServiceException {
        try {
            return dao.findTattooById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Tattoo findTattooById(int id) throws ServiceException {
        try {
            return dao.findTattooById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean deleteTattoo(Tattoo tattoo) throws ServiceException {
        try {
            return dao.delete(tattoo);
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


    public boolean updateTattooRating(Tattoo tattoo, User user, String rating) throws ServiceException {
        try {
            dao.addRate(user.getId(), tattoo.getId(), Integer.parseInt(rating));
            BigDecimal newRating = countRating(tattoo);
            return dao.updateRating(tattoo.getId(), newRating);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BigDecimal countRating(Tattoo tattoo) throws ServiceException {
        try {
            double sum = dao.findRatesSum(tattoo.getId());
            double quantity = dao.findRatesQuantity(tattoo.getId());
            return new BigDecimal(sum / quantity).setScale(2, RoundingMode.CEILING);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean hasAlreadyRated(Tattoo tattoo, User user) throws ServiceException {
        try {
            return dao.hasAlreadyRated(user.getId(), tattoo.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
