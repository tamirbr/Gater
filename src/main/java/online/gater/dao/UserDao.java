package online.gater.dao;

import online.gater.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(Long id);
    User findByEmail(String username);
    void save(User user);
    void update(User user);
    void delete(Long id);
}
