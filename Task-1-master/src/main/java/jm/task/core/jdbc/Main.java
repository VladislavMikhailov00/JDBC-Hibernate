package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.util.Scanner;

public class Main {
    private static boolean flag = true;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        while (flag) {
            System.out.println("Выберите вариант работы: " +
                    "\n1) Работа через JDBC." +
                    "\n2) Работа через Hibernate.");

            int input = 0;
            try {
                input = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("Введён неверный параметр, перезапустите программу");
                return;
            }

            if (input == 1) {
                while (flag) {
                    try {
                        jdbcWorks();
                    } catch (Exception e) {
                        jdbcWorks();
                    }
                }
            } else if (input == 2) {
                while (flag) {
                    try {
                        hibernateWorks();
                    } catch (Exception e) {
                        hibernateWorks();
                    }
                }
            }
        }
        scanner.close();
    }

    public static void jdbcWorks() {

        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        System.out.println("--------------------------");
        System.out.println("Выберите действие: " +
                "\n1) Создать таблицу." +
                "\n2) Удалить таблицу." +
                "\n3) Добавить пользователя в таблицу." +
                "\n4) Удалить пользователя из таблицы." +
                "\n5) Вывести всех пользователей." +
                "\n6) Очистить таблицу." +
                "\n7) Выход из программы.");
        System.out.println("--------------------------");

        int number = scanner.nextInt();

        switch (number) {
            case 1:
                userDao.createUsersTable();
                break;
            case 2:
                userDao.dropUsersTable();
                break;
            case 3:
                System.out.println("Введите имя: ");
                String name = scanner.next();
                System.out.println("Введите фамилию: ");
                String lastname = scanner.next();
                System.out.println("Введите возраст: ");
                userDao.saveUser(name, lastname, ageUser(scanner.next()));
                break;
            case 4:
                System.out.println("Введите id, который хотите удалить: ");
                long id = scanner.nextLong();
                userDao.removeUserById(id);
                break;
            case 5:
                userDao.getAllUsers().forEach(System.out::println);
                break;
            case 6:
                userDao.cleanUsersTable();
                break;
            case 7:
                flag = false;
                break;
        }
    }

    public static void hibernateWorks(){

        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();

        System.out.println("--------------------------");
        System.out.println("Выберите действие: " +
                "\n1) Создать таблицу." +
                "\n2) Удалить таблицу." +
                "\n3) Добавить пользователя в таблицу." +
                "\n4) Удалить пользователя из таблицы." +
                "\n5) Вывести всех пользователей." +
                "\n6) Очистить таблицу." +
                "\n7) Выход из программы.");
        System.out.println("--------------------------");

        int number = scanner.nextInt();

        switch (number) {
            case 1:
                userDao.createUsersTable();
                break;
            case 2:
                userDao.dropUsersTable();
                break;
            case 3:
                System.out.println("Введите имя: ");
                String name = scanner.next();
                System.out.println("Введите фамилию: ");
                String lastname = scanner.next();
                System.out.println("Введите возраст: ");
                userDao.saveUser(name, lastname, ageUser(scanner.next()));
                break;
            case 4:
                System.out.println("Введите id, который хотите удалить: ");
                long id = scanner.nextLong();
                userDao.removeUserById(id);
                break;
            case 5:
                userDao.getAllUsers().forEach(System.out::println);
                break;
            case 6:
                userDao.cleanUsersTable();
                break;
            case 7:
                flag = false;
                break;
        }
    }

    public static Byte ageUser(String value){
        byte age = 0;
        try {
            age = Byte.parseByte(value);
            if (age <= 0) {
                throw new RuntimeException("Возраст не может быть меньше или равен нулю");
            }
        } catch (NumberFormatException e) {
            System.err.println("Введено не целочисленное значение " + e.getMessage());
            return null;
        }
        return age;
    }
}