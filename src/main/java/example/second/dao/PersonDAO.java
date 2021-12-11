package example.second.dao;

import example.second.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int count;
    private List<Person> persons;

    {
        persons = new ArrayList<>();
        persons.add(new Person(++count, "Tom"));
        persons.add(new Person(++count, "Bob"));
        persons.add(new Person(++count, "Any"));
        persons.add(new Person(++count, "Sam"));
    }

    public List<Person> index() {
        return persons;
    }

    public Person show(int index) {
        return persons.stream().filter(person -> person.getId() == index).findAny().orElse(null);
    }
}