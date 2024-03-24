package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.auth.JwtUtil;
import com.fiap.techchallenge.fourworktimeapp.application.dto.*;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.EmployeeUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeUseCase employeeUseCase;
    private JwtUtil jwtUtil;

    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO login) {
        log.info("incoming login request [username:{}][registry:{}]", login.getUsername(), login.getRegistry());
        var usernameOrRegistry = login.getUsername() != null && !login.getUsername().isBlank() ? login.getUsername() : login.getRegistry();
        var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrRegistry, login.getPassword()));
        var registry = authentication.getName();
        var token = jwtUtil.createToken(login.toEmployee());
        var loginRes = new AuthLoginResponseDTO(new AuthLoginDataResponseDTO(registry, token));
        loginRes.add(linkTo(methodOn(AuthController.class).login(login)).withSelfRel());

        return ResponseEntity.ok(loginRes);
    }


    @PostMapping(value = "/register", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<AuthRegisterResponseDTO> register(@RequestBody AuthRegisterRequestDTO register) {
        var employee = register.toWorkerEmployee();
        var registeredEmployee = employeeUseCase.registerEmployee(employee);
        var response = new AuthRegisterResponseDTO(AuthRegisterDataResponseDTO.fromEmployee(registeredEmployee));
        response.add(linkTo(methodOn(AuthController.class).register(register)).withSelfRel());

        return ResponseEntity.ok(response);
    }

}
