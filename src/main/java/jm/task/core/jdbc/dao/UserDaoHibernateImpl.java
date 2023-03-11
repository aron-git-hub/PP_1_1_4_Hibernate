package jm.task.core.jdbc.dao;

import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS user (
                id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                name VARCHAR(30) NOT NULL,
                lastname VARCHAR(40) NOT NULL,
                age TINYINT NULL)""";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user";
    private static final String GET_ALL_USERS = "SELECT id, name, lastname, age FROM user";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE user";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(DROP_TABLE)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
//            session.createNativeQuery("INSERT INTO user (name, lastName, age) VALUES ('" + name + "', '" +
//                            lastName + "', " + age + ")")
//                    .addEntity(User.class)
//                    .executeUpdate();

            //depricated since 6.0
            session.save(user);
//            session.persist(user);

            session.getTransaction().commit();
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
//            session.createNativeQuery("DELETE FROM user WHERE id =" + id)
//                    .addEntity(User.class)
//                    .executeUpdate();

            User user = session.get(User.class,id);
//            Deprecated since 6.0
//            session.delete(user);
            session.remove(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createNativeQuery(GET_ALL_USERS)
                    .addEntity(User.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CLEAN_TABLE)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}