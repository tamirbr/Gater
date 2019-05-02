package online.gater.service;

import online.gater.dao.UserDao;
import online.gater.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        if(user == null) {
        	throw new UsernameNotFoundException(username);
        }
        return user;
    }


}
