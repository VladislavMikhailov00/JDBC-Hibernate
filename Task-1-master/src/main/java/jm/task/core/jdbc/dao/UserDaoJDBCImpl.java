package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private UserServiceImpl userService = new UserServiceImpl();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            userService.createUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            userService.dropUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            userService.saveUser(name, lastName, age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            userService.removeUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            List <User> list= userService.getAllUsers();
            return list;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            userService.cleanUsersTable();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}