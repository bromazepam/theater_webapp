package com.theater.app.bootstrap;

import com.theater.app.domain.User;
import com.theater.app.domain.security.Role;
import com.theater.app.domain.security.UserRole;
import com.theater.app.repository.RoleRepository;
import com.theater.app.repository.UserRepository;
import com.theater.app.service.UserService;
import com.theater.app.utility.SecurityUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class TheaterBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public TheaterBootstrap(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user1.setEmail("admin@gmail.com");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1= new Role();
        role1.setRoleId("0");
        role1.setName("ROLE_ADMIN");
//        roleRepository.save(role1);
        userRoles.add(new UserRole(user1, role1));

        userService.createUser(user1, userRoles);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(SecurityUtility.passwordEncoder().encode("user"));
        user2.setEmail("user@gmail.com");
        Set<UserRole> userRoles2 = new HashSet<>();
        Role role2= new Role();
        role2.setRoleId("1");
        role2.setName("ROLE_USER");
//        roleRepository.save(role2);
        userRoles2.add(new UserRole(user2, role2));

        userService.createUser(user2, userRoles2);
    }
}
