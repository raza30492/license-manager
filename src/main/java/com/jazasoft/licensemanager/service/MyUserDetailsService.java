package com.jazasoft.licensemanager.service;

import com.jazasoft.licensemanager.dto.UserDto;
import com.jazasoft.licensemanager.entity.User;
import com.jazasoft.licensemanager.respository.UserRepository;
import com.jazasoft.licensemanager.util.Utils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mdzahidraza on 26/06/17.
 */
@Service
@Transactional(readOnly = true)
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.trace("Looking for user for {}", username);
        try {
            Optional<User> user = userRepository.findOneByUsername(username);
            if (!user.isPresent()) {
                user = userRepository.findOneByEmail(username);
                if (!user.isPresent()) {
                    LOGGER.info("USER NOT PRESENT for {}", username);
                    throw new UsernameNotFoundException("user not found");
                }
            }
            LOGGER.trace("Found user for {}", username);
            return user.get();
        } catch (Exception e) {
            LOGGER.error("Error loading user {}", username, e);
        }
        return null;
    }


    @Autowired
    Mapper mapper;

    public UserDto findOne(Long id) {
        LOGGER.debug("findOne(): id = {}",id);
        User user = userRepository.findOne(id);
        if (user != null) {
            return mapper.map(userRepository.findOne(id), UserDto.class);
        }
        return null;
    }

    public List<UserDto> findAll() {
        LOGGER.debug("findAll()");
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllAfter(long after) {
        LOGGER.debug("findAllAfter(): after = {}" , after);
        return userRepository.findByModifiedAtGreaterThan(new Date(after)).stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto findByEmail(String email) {
        LOGGER.debug("findByEmail(): email = {}",email);
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return mapper.map(user, UserDto.class);
        }
        return null;
    }

    public UserDto findByName(String name) {
        LOGGER.debug("findByName(): name = " , name);
        User user = userRepository.findByName(name);
        if (user != null) {
            return mapper.map(user, UserDto.class);
        }
        return null;
    }

    public UserDto findByUsername(String username) {
        LOGGER.debug("findByUsername(): username = " , username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return mapper.map(user, UserDto.class);
        }
        return null;
    }

    public Boolean exists(Long id) {
        LOGGER.debug("exists(): id = ",id);
        return userRepository.exists(id);
    }

    public Long count(){
        LOGGER.debug("count()");
        return userRepository.count();
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        LOGGER.debug("save()");
        User user = mapper.map(userDto, User.class);
        user.setRoles(Utils.getRoles(userDto.getRoles()));
        user.setPassword(userDto.getMobile());
        user.setEnabled(true);
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        LOGGER.debug("update()");
        User user = userRepository.findOne(userDto.getId());
        user = mapper.map(userDto, User.class);
        return mapper.map(user, UserDto.class);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete(): id = {}",id);
        userRepository.delete(id);
    }
}
