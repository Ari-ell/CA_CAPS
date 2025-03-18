package com.team1.sa56.caps;

import com.team1.sa56.caps.controller.CommonController;
import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.serviceImpl.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CommonControllerTest {
    @Mock
    private UserServiceImpl userService;

    private User user;
    @InjectMocks
    private CommonController commonController;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("jaiyi@iss.com");
        user.setPassword("Jiayi");
        mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();

    }
    @Test
    public void testLoginSucess() throws Exception{
        when(userService.validateAccount(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/login/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("/testPage"));
    }

    @Test
    public void testLoginFail() throws Exception{
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("testpassword");
        when(userService.validateAccount(any(User.class))).thenReturn(null);
        mockMvc.perform(post("/login/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user)).andExpect(view().name("/login"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Wrong password"));
    }



}
