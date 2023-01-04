package example.second.dao;

import example.second.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM usertable", new UserMapper());
    }

    public List<User> indexV2() {
        return jdbcTemplate.query("SELECT * FROM usertable", new BeanPropertyRowMapper<>(User.class));
    }

    public User show(int index) {
        return jdbcTemplate.query("SELECT * FROM usertable WHERE id=?", new Object[]{index}, new UserMapper())
                .stream().findAny().orElse(null);
    }

    public User showV2(int index) {
        return jdbcTemplate.query("SELECT * FROM usertable WHERE id=?", new Object[]{index}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO usertable VALUES(1,?,?,?)",
                user.getName(),
                user.getAge(),
                user.getEmail()
        );
    }

    public void update(int id, User updateuser) {
        jdbcTemplate.update("UPDATE usertable SET name=?, age=?, email=? WHERE id=?",
                updateuser.getName(),
                updateuser.getAge(),
                updateuser.getEmail(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FORM usertable WHERE id=?", id);
    }

    ////
    //// Тестируем производительность пакетной вставки
    ////

    public void testBatchUpdate() {
        List<User> users = create1000Users();

        jdbcTemplate.batchUpdate(
                "INSERT INTO usertable VALUES (?,?,?,?)"
                ,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, users.get(i).getId());
                        preparedStatement.setString(2, users.get(i).getName());
                        preparedStatement.setInt(3, users.get(i).getAge());
                        preparedStatement.setString(4, users.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return 1000;
                    }
                });
    }

    private List<User> create1000Users() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            users.add(new User(i, 30 + i, "User " + i, "test" + i + "@fd.com"));
        }

        return users;
    }


}
