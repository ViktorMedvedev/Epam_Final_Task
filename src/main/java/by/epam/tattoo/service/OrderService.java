package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.order.OrderDao;
import main.java.by.epam.tattoo.dao.user.UserDao;
import main.java.by.epam.tattoo.entity.order.Order;
import main.java.by.epam.tattoo.entity.tattoo.Tattoo;
import main.java.by.epam.tattoo.entity.user.User;
import main.java.by.epam.tattoo.util.ConstantHeap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class OrderService {
    private OrderDao dao = new OrderDao();

    public Order registerOrder(String userId, String tattooId) throws ServiceException {
        Order order = new Order(Integer.parseInt(userId), Integer.parseInt(tattooId), countDiscount(userId, tattooId));
        try {
            return dao.register(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Order> findAllOrders() throws ServiceException {
        try {
            return dao.findAllOrders();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Order> findUserOrders(String id) throws ServiceException {
        try {
            return dao.findUserOrders(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BigDecimal countDiscount(String userId, String tattooId) throws ServiceException {
        UserService userService = new UserService();
        TattooService tattooService = new TattooService();

        User user = userService.findUserById(userId);
        Tattoo tattoo = tattooService.findTattooById(tattooId);
        BigDecimal price = tattoo.getPrice();
        BigDecimal pct = BigDecimal.valueOf(user.getDiscountPct());
        return price.subtract(price.multiply(pct.multiply(ConstantHeap.PERCENT_CONVERTER))).
                setScale(2, RoundingMode.CEILING);
    }
}
