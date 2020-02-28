package com.project.server.business;

import com.project.server.dal.dao.UserDao;
import com.project.server.dal.repository.UserRepository;
import openapi.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;
	
	public UserDao addUser(UserDao userDao) {
		return userRepository.save(userDao);
	}
	
	public void deleteUserById(UUID id) {
		userRepository.deleteUserByUserId(id);
	}
	
	public UserDao findUserById(UUID id) {
		return userRepository.findByUserId(id);
	}
	
	public List<UserDao> findAllUsers(){
		return userRepository.findAll();
	}

	public UserDao findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDao loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao userDao = userRepository.findByUsername(username);
		
        if (userDao == null) {
            throw new UsernameNotFoundException(String.format("No userDao found with username '%s'.", username));
        } else {
			logger.info("UserDao from DB. userId: " + userDao.getUserId() + " username: " + userDao.getUsername());
            return userDao;
        }
	}

	public void saveUser(User user) {
		UserDao userDao = new UserDao();

		userDao.setEmail(user.getEmail());
		userDao.setUsername(user.getUsername());
		userDao.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.setRole(user.getRole());
		userDao.setCreationTimestamp(new Date());
		userDao.setLastUpdateTimestamp(new Date());

		userRepository.save(userDao);
	}

}
