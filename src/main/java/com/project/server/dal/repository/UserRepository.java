package com.project.server.dal.repository;

import com.project.server.dal.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDao, UUID> {

    void deleteUserByUserId(UUID id);

    UserDao findByUserId(UUID id);

    UserDao findByUsername(String username);

    List<UserDao> findAll();

    UserDao findByEmail(String email);

}
