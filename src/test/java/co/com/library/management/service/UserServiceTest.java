package co.com.library.management.service;

import co.com.library.management.dto.UserDTO;
import co.com.library.management.model.User;
import co.com.library.management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void createUser() {

        var user = new User();
        user.setId("123");
        user.setName("Alberto Sánchez");

        var userDTO = new UserDTO();
        user.setId("123");
        user.setName("Alberto Sánchez");

        Mockito.when(userRepository.save(any())).thenReturn(user);

        var response = userService.createUser(userDTO);
        Assertions.assertEquals(user.getName(), response.getName());

    }

    @Test
    void findById() {

        var user = new User();
        user.setId("123");
        user.setName("Alberto Sánchez");

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var response = userService.findById("123");
        Assertions.assertEquals(user.getName(), response.getName());
        Assertions.assertEquals(user.getNameResource(), response.getNameResource());

    }
}