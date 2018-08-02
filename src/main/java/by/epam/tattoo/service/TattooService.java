package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.tattoo.TattooDao;
import main.java.by.epam.tattoo.entity.tattoo.Tattoo;

import java.util.ArrayList;

public class TattooService {
    private TattooDao dao = new TattooDao();

    public ArrayList<Tattoo> findAllTattoos() throws ServiceException {
        try {
            return dao.findAllTattoos();
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

    public byte[] findTattooImage(String id) throws ServiceException {
        try {
            return dao.findTattooImageById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
