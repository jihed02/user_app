package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public List<Person> getAllPersons() {
        return repo.findAll();
    }

    public Person getPersonById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Person savePerson(Person Person) {
        return repo.save(Person);
    }

    public Person updatePerson(Long id, Person newPerson) {
        return repo.findById(id).map(Person -> {
            Person.setName(newPerson.getName());
            Person.setEmail(newPerson.getEmail());
            return repo.save(Person);
        }).orElse(null);
    }

    public void deletePerson(Long id) {
        repo.deleteById(id);
    }
}