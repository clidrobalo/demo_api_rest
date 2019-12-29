package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDateAccessService implements PersonDao {

    private static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id, person.getName()));
        return 0;
    }

    @Override
    public List<Person> selectAllPerson() {
        return db;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if(personMaybe.isPresent()){
            db.remove(personMaybe);
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return selectPersonById(id)
                .map(p -> {
                    int indexOfPerson = db.indexOf(person);
                    if(indexOfPerson >= 0) {
                        db.set(indexOfPerson, person);
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

}
