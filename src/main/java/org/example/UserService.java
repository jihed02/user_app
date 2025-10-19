package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public User updateUser(Long id, User newUser) {
        return repo.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            return repo.save(user);
        }).orElse(null);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}