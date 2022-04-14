package com.theater.app.repository;

import com.theater.app.domain.security.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteById("1");
    }

    @Test
    void findByName() {
        //given
        Role role = Role.builder()
                .name("superAdmin")
                .roleId("1").build();
        roleRepository.save(role);
        //when
        Role expected = roleRepository.findByName("superAdmin");
        //then
        assertThat(expected.getName()).isEqualTo(role.getName());
    }
}