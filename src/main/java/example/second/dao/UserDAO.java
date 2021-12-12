package example.second.dao;

import example.second.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    private static int count;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String USERPASSWORD = "0";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver"); // с помощью рефлексии подгружаем класс Driver и удостоверяемся, что класс загружен в оперативную память
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, USERPASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> index() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM usertable";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setEmail(resultSet.getString("email"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User show(int index) {
        return null;//users.stream().filter(user -> user.getId() == index).findAny().orElse(null);
    }

    public void save(User user) {
//        user.setId(++count);
//        users.add(user);
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO usertable VALUES(" + 1 + ",'" + user.getName() +
                    "'," + user.getAge() + ",'" + user.getEmail() + "')";
            //INSERT INTO usertable VALUES(1,'getName',getAge,'getEmail'
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, User updateuser) {
/*        User userToBeUpdated = show(id);
        userToBeUpdated.setName(updateuser.getName());
        userToBeUpdated.setAge(updateuser.getAge());
        userToBeUpdated.setEmail(updateuser.getEmail());*/
    }

    public void delete(int id) {
       // users.removeIf(user -> user.getId() == id);
    }
}
