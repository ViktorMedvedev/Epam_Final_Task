package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.user.UserDao;
import main.java.by.epam.tattoo.entity.Role;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.util.ConstantHeap;

import java.util.ArrayList;

public class UserService {
    private UserDao dao = new UserDao();

    public User registerUser(String email, String login, String password) throws ServiceException {
        User user = new User(email, login, password);
        try {
            return dao.register(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public User loginUser(String login, String password) throws ServiceException {
        User user = new User(login, password);
        try {
            return dao.login(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User changePassword(String login, String oldPassword, String newPassword) throws ServiceException {
        User user = new User(login, oldPassword);
        try {
            return dao.changeUserPassword(user, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User findUserById(String id) throws ServiceException {
        try {
            return dao.findUserById(Integer.parseInt(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User findUserById(int id) throws ServiceException {
        try {
            return dao.findUserById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateUserDiscount(int id) throws ServiceException {
        try {
            int discount = findUserById(id).getDiscountPct();
            if (discount < ConstantHeap.MAX_DISCOUNT_VALUE) {
                discount += ConstantHeap.DEFAULT_DISCOUNT_CHARGE;
                return dao.updateDiscount(id, discount);
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    public boolean updateUserRole(String id) throws ServiceException {
        String role;
        try {
            if (findUserById(id).getRole().equals(Role.USER)) {
                role = Role.MODERATOR.toString();

            }else{
                role = Role.USER.toString();
            }
            return dao.updateRole(Integer.parseInt(id), role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<User> findAllUsers() throws ServiceException {
        try {
            return dao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean deleteUser(User user) throws ServiceException {
        OfferService offerService = new OfferService();
        OrderService orderService = new OrderService();
        try {
            return dao.delete(user)
                    && offerService.deleteUserOffers(user.getId())
                    && orderService.deleteUserOrders(user.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

