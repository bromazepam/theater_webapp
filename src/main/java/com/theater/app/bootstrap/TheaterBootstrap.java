package com.theater.app.bootstrap;

import com.theater.app.domain.User;
import com.theater.app.domain.security.Role;
import com.theater.app.repository.RoleRepository;
import com.theater.app.service.UserService;
import com.theater.app.security.SecurityUtility;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class TheaterBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User user1 = new User();
        user1.setId("0");
        user1.setUsername("admin");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user1.setEmail("admin@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        Role role1= new Role();
        role1.setRoleId("0");
        role1.setName("ROLE_ADMIN");
        roleRepository.save(role1);
        userRoles.add(role1);

        userService.createUser(user1, userRoles);

        User user2 = new User();
        user2.setId("1");
        user2.setUsername("user");
        user2.setPassword(SecurityUtility.passwordEncoder().encode("user"));
        user2.setEmail("user@gmail.com");
        Set<Role> userRoles2 = new HashSet<>();
        Role role2= new Role();
        role2.setRoleId("1");
        role2.setName("ROLE_USER");
        roleRepository.save(role2);
        userRoles2.add(role2);

        userService.createUser(user2, userRoles2);
    }
}
