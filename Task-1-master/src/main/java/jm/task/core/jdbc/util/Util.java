package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соединения с БД
    private static SessionFactory sessionFactory;
    private static Properties properties;

    static {
        try (InputStream input = Util.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            properties = new Properties();
            properties.load(input);

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .applySetting("hibernate.connection.url", properties.getProperty("db.url"))
                    .applySetting("hibernate.connection.username", properties.getProperty("db.username"))
                    .applySetting("hibernate.connection.password", properties.getProperty("db.password"))
                    .applySetting("hibernate.dialect", properties.getProperty("hibernate.dialect"))
                    .applySetting("hibernate.show_sql", properties.getProperty("hibernate.show_sql"))
                    .applySetting("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"))
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .buildMetadata()
                    .buildSessionFactory();

        } catch (Exception e) {
            throw new ExceptionInInitializerError("Ошибка инициализации Hibernate: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }
}