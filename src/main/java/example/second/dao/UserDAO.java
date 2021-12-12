package example.second.dao;

import example.second.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    private static int count;
    private List<User> users;

    {
        users = new ArrayList<>();
        users.add(new User(++count, 14, "Tom", "@mil"));
        users.add(new User(++count, 16, "Bob", "@gugle"));
        users.add(new User(++count, 18, "Any", "@yahu"));
        users.add(new User(++count, 20, "Sam", "@yandx"));
    }

    public List<User> index() {
        return users;
    }

    public User show(int index) {
        return users.stream().filter(user -> user.getId() == index).findAny().orElse(null);
    }

    public void save(User user) {
        user.setId(++count);
        users.add(user);
    }

    public void update(int id, User updateuser) {
        User userToBeUpdated = show(id);
        userToBeUpdated.setName(updateuser.getName());
        userToBeUpdated.setAge(updateuser.getAge());
        userToBeUpdated.setEmail(updateuser.getEmail());
    }

    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
