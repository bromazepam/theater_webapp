package com.theater.app.repository;

import com.theater.app.domain.security.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown(){
        roleRepository.deleteById("10");
    }

    @Test
    void findByName() {
        //given
        Role role = new Role("10","superAdmin");
        roleRepository.save(role);
        //when
        Optional<Role> expected = roleRepository.findByName("superAdmin");
        //then
        assertThat(expected.get().getName()).isEqualTo(role.getName());
    }
}