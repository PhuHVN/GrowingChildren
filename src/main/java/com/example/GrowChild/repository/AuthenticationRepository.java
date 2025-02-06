package com.example.GrowChild.repository;


import com.example.GrowChild.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByRole_RoleName(String roleName);
    List<User> findByRole_RoleId(long role_id);

}
