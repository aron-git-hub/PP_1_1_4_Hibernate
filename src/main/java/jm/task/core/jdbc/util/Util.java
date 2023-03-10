package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String URL = "jdbc:mysql://localhost:3306/usrdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
//    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("CONNECTION FAILED");
            e.printStackTrace();
        }
        return connection;
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties props = new Properties();
                props.put(Environment.URL, URL);
                props.put(Environment.USER, USERNAME);
                props.put(Environment.PASS, PASSWORD);
//                props.put(Environment.DIALECT, DIALECT);

                Configuration config = new Configuration()
                        .setProperties(props).addAnnotatedClass(User.class);

                ServiceRegistry serviceReg = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();

                sessionFactory = config.buildSessionFactory(serviceReg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}