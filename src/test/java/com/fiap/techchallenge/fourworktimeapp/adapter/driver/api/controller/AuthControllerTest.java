package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourworktimeapp.application.auth.JwtUtil;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthLoginRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthLoginResponseDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthRegisterRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.domain.employee.entity.Employee;
import com.fiap.techchallenge.fourworktimeapp.domain.employee.usecase.EmployeeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @Mock
    private EmployeeUseCase employeeUseCase;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }
    
    @Test
    void shouldLogin() throws Exception {
        // Arranct
        var loginRequest = new AuthLoginRequestDTO("registry-test", "test-pass");
        var authentication = new UsernamePasswordAuthenticationToken(loginRequest.getRegistry(), loginRequest.getPassword());
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.createToken(any())).thenReturn("TestJWTToken");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                .content(asJsonString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegister() throws Exception {
        var registerRequest = new AuthRegisterRequestDTO("registry-test", "test-pass");
        var employee = Employee.builder()
                .registry(registerRequest.getRegistry())
                .password(registerRequest.getPassword())
                .build();

        when(employeeUseCase.registerEmployee(any(Employee.class))).thenReturn(employee);
        when(jwtUtil.createToken(any())).thenReturn("TestJWTToken");

        mockMvc.perform(post("/v1/auth/register")
                        .content(asJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}