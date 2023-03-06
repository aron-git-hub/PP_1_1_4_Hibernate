package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS user (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                    name VARCHAR(30) NOT NULL,
                    lastname VARCHAR(40) NOT NULL,
                    age TINYINT NULL);""";

        try (Statement state = Util.getConnection().createStatement()) {
            state.executeUpdate(query);
            System.out.println("TABLE USER CREATED");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        String query = "DROP TABLE IF EXISTS user;";

        try(Statement state = Util.getConnection().createStatement()) {
            state.executeUpdate(query);

            System.out.println("TABLE USER DROPPED");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String query = "INSERT INTO user (name, lastname, age) VALUES(?, ?, ?);";

        try(PreparedStatement pState = Util.getConnection().prepareStatement(query)) {
            pState.setString(1, name);
            pState.setString(2, lastName);
            pState.setByte(3, age);

            pState.executeUpdate(query);

            System.out.println("USER SAVED");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user WHERE id = ?;";
        try(PreparedStatement pState = Util.getConnection().prepareStatement(query)) {
            pState.setLong(1, id);

            pState.executeUpdate(query);

            System.out.println("USER DELETED");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
