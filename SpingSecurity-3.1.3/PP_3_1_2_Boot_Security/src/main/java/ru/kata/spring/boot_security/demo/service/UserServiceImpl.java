package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void update(int id, User user) {
        user.setId(id);
        userRepository.save(user);
    }
    @Transactional
    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
    }
    public User findUserByName(String username) {
        return getUserList().stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }
}
