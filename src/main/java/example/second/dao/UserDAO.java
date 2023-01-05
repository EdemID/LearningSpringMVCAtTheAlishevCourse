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
import java.util.Optional;

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

    public User showWithBeanPropertyRowMapper(int index) {
        return jdbcTemplate.query("SELECT * FROM usertable WHERE id=?", new Object[]{index}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public Optional<User> show(String email) {
        return jdbcTemplate.query("SELECT * FROM usertable WHERE email=?", new Object[]{email}, new UserMapper())
                .stream().findAny();
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO usertable (name, age, email) VALUES(?,?,?)",
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

    public void testBatchUpdate() {
        List<User> users = create1000Users();

        jdbcTemplate.batchUpdate(
                "INSERT INTO usertable (name, age, email) VALUES(?,?,?)"
                ,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, users.get(i).getName());
                        preparedStatement.setInt(2, users.get(i).getAge());
                        preparedStatement.setString(3, users.get(i).getEmail());
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
            users.add(new User(i, 30 + i, "User " + i, "test" + i + "@fd.com", "Moscow"));
        }

        return users;
    }
}
