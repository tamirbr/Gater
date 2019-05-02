package online.gater.service;

import online.gater.dao.UserDao;
import online.gater.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void update(User user) {
        userDao.update(user);		
	}
}
