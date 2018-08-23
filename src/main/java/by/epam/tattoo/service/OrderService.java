package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.order.OrderDao;
import main.java.by.epam.tattoo.entity.Order;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.util.ConstantHeap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class OrderService {
    private OrderDao dao = new OrderDao();

    public Order registerOrder(int userId, int tattooId) throws ServiceException {
        Order order = new Order(userId, tattooId, countDiscount(userId, tattooId));
        try {
            return dao.register(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Order findOrderByUserIdAndTattooId(int userId, int tattooId) throws ServiceException {
        try {
            return dao.findOrderByUserIdAndTattooId(userId, tattooId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    public Order findOrderById(int orderId) throws ServiceException {
        try {
            return dao.findOrderById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    public boolean deleteOrder(Order order) throws ServiceException {
        try {
            return dao.delete(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    boolean deleteUserOrders(int userId) throws ServiceException {
        try {
            return dao.delete(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    public boolean updateOrder(String status, int orderId) throws ServiceException {
        try {
            return dao.updateOrder(status, orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Order> getOrderList(User user) throws ServiceException {
        try {
            if (user.getRole().equals(Role.ADMIN)) {
                return dao.findAllOrders();
            }
            return dao.findUserOrders(user.getId());

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private BigDecimal countDiscount(int userId, int tattooId) throws ServiceException {
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
