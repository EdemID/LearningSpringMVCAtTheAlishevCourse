package example.second.dao;

import example.second.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
