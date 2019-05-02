package online.gater.dao;

import online.gater.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        List<User> users = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return users;
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class,id);
        session.close();
        return user;
    }

    @Override
    public User findByEmail(String username) {
        Session session = sessionFactory.openSession();
        String hql = "from User where email = '"+username+"'";
        User user;
        try{
            user = (User)session.createQuery(hql).list().get(0);
        }catch (Exception e){
            user = null;
        }
        session.close();
        return user;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class,id);
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

	@Override
	public void update(User user) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();		
	}
}
