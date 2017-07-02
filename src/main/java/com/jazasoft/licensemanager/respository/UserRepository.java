package com.jazasoft.licensemanager.respository;

import com.jazasoft.licensemanager.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
    
    User findByName(String name);

    User findByUsername(String username);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmail(String email);

    List<User> findByModifiedAtGreaterThan(Date updatedAt);
}
