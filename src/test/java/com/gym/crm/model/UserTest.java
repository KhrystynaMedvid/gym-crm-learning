package com.gym.crm.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void noArgsConstructor_createsEmptyUser() {
        User user = new User();

        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertFalse(user.isActive());
    }

    @Test
    void uilder_createsUserWithAllFields(){
        User user = User.builder()
                .id(1L)
                .firstName("Name")
                .lastName("LastName")
                .isActive(true)
                .password("68696")
                .username("marry")
                .build();

        assertEquals(user.getFirstName(), "Name");
    }

}