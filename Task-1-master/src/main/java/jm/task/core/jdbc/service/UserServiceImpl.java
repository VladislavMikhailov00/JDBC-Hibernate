package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private Util util = new Util();
    private Connection connection;

    public void createUsersTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS public.users(
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255),
                    last_name VARCHAR(255),
                    age INT
                )
                """;
        connection = util.getConnection();
        var stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
    }

    public void dropUsersTable() throws SQLException {
        String sql = """
                DROP TABLE IF EXISTS public.users;
                """;
        connection = util.getConnection();
        var stmt = connection.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        connection = util.getConnection();
        var stmt = connection.prepareStatement("INSERT INTO public.users(name, last_name, age) VALUES (?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, lastName);
        stmt.setByte(3, age);
        stmt.executeUpdate();
        stmt.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных ");
    }

    public void removeUserById(long id) throws SQLException {
        connection = util.getConnection();
        var stmt = connection.prepareStatement("DELETE FROM public.users WHERE id = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<User> getAllUsers() throws SQLException {
        connection = util.getConnection();
        var stmt = connection.prepareStatement("SELECT * FROM public.users");
        ResultSet resultSet = stmt.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("last_name"),
                    resultSet.getByte("age")));
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String sgl = """
                TRUNCATE TABLE public.users;
                """;
        connection = util.getConnection();
        var stmt = connection.prepareStatement(sgl);
        stmt.executeUpdate();
        stmt.close();
    }
}