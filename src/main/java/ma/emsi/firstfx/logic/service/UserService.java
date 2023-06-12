package ma.emsi.firstfx.logic.service;

import ma.emsi.firstfx.logic.dao.UserDao;
import ma.emsi.firstfx.logic.dao.impl.UserDaoImp;

public class UserService {
    private UserDao userDao = new UserDaoImp();

    public boolean isValidate(String username, String password) {
        return userDao.isValidate(username, password);
    }
}