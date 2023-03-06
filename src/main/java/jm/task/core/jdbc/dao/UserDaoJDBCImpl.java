package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS user (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                    name VARCHAR(30) NOT NULL,
                    lastname VARCHAR(40) NOT NULL,
                    age TINYINT NULL)""";

        try (Statement state = Util.getConnection().createStatement()) {
            state.executeUpdate(query);
            System.out.println("TABLE CREATED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS user";

        try (Statement state = Util.getConnection().createStatement()) {
            state.executeUpdate(query);
            System.out.println("TABLE DROPPED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user (name, lastname, age) VALUES(?, ?, ?)";

        try (PreparedStatement pState = Util.getConnection().prepareStatement(query)) {
            pState.setString(1, name);
            pState.setString(2, lastName);
            pState.setByte(3, age);

            pState.executeUpdate();
            System.out.println("USER SAVED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement pState = Util.getConnection().prepareStatement(query)) {
            pState.setLong(1, id);

            pState.executeUpdate();
            System.out.println("USER DELETED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT name, lastname, age FROM user";
        List<User> userList = new ArrayList<>();

        try (Statement state = Util.getConnection().createStatement()) {
            ResultSet resultSet = state.executeQuery(query);

            while(resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }

            System.out.println("USER LIST CREATED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE user";

        try (Statement state = Util.getConnection().createStatement()) {
            state.executeUpdate(query);
            System.out.println("TABLE CLEARED");
            System.out.println("▼ CONNECTION CLOSE");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
