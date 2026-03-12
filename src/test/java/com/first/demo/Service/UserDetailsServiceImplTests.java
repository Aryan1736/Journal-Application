package com.first.demo.Service;

import com.first.demo.Entity.User;
import com.first.demo.Repository.UserRepository;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;


public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void loadUserByUsername(){
         when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("A").password("A").roles(new ArrayList<>()).build());
         UserDetails user = userDetailsService.loadUserByUsername("A");
        Assertions.assertNotNull(user);
     }

}
