package com.fiap.techchallenge.fourworktimeapp.application.usecase;

import com.fiap.techchallenge.fourworktimeapp.adapter.driven.data.repository.EmployeeRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeDetailServiceUseCase implements UserDetailsService {

    private final EmployeeRepositoryImpl repository;

    @Override
    public UserDetails loadUserByUsername(String registry) throws UsernameNotFoundException {
        var employee = repository.findEmployeeByRegistry(registry);
        List<String> roles = new ArrayList<>();
        roles.add(employee.getRole().toString());
        return User.builder()
                        .username(employee.getRegistry())
                        .password(employee.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
    }
}
