package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.dao.DaoException;
import main.java.by.epam.tattoo.dao.user.UserDao;
import main.java.by.epam.tattoo.entity.user.Role;
import main.java.by.epam.tattoo.entity.user.User;

public class UserService {
    private UserDao dao = new UserDao();

    public User registerUser(String email, String login, String password, Role role) throws ServiceException {
        User user = new User(email, login, password, role);
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

}

