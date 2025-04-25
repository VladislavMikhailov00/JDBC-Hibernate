package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.Cleanup;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session = null;
    private Util util = new Util();

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = """
                     CREATE TABLE IF NOT EXISTS public.users(
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         last_name VARCHAR(255),
                         age INT
                     )
                     """;
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = """
                     DROP TABLE IF EXISTS public.users;
                     """;
        session.createNativeQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных ");
    }

    @Override
    public void removeUserById(long id) {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User",User.class).list();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        @Cleanup Session session = util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE public.users;").executeUpdate();
        session.getTransaction().commit();
    }
}