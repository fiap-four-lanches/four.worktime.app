package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.auth.JwtUtil;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthLoginRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthLoginResponseDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthRegisterRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.application.dto.AuthRegisterResponseDTO;
import com.fiap.techchallenge.fourworktimeapp.domain.employee.usecase.EmployeeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeUseCase employeeUseCase;
    private JwtUtil jwtUtil;

    @ResponseBody
    @PostMapping(value = "/login" , produces = "application/json")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO login) {
        var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getRegistry(), login.getPassword()));
        var registry = authentication.getName();
        var token = jwtUtil.createToken(login.toEmployee());
        var loginRes = new AuthLoginResponseDTO(registry, token);

        return ResponseEntity.ok(loginRes);
    }


    @PostMapping(value = "/register", headers="Accept=application/json", produces = "application/json")
    public ResponseEntity<AuthRegisterResponseDTO> register(@RequestBody AuthRegisterRequestDTO register) {
        var employee = register.toWorkerEmployee();
        var registeredEmployee = employeeUseCase.registerEmployee(employee);
        var response = AuthRegisterResponseDTO.fromEmployee(registeredEmployee);

        return ResponseEntity.ok(response);
    }

}
