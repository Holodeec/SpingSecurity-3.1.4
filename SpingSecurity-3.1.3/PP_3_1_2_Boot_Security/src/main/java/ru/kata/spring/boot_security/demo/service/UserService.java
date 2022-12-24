package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
@Service
public interface UserService {
    void save(User user);
    void removeUser(int id);
    void update(int id, User user);
    List<User> getUserList();
    public User getUserById(int id);
}
