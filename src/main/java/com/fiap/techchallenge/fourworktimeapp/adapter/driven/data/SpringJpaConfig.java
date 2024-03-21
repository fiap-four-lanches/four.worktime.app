package com.fiap.techchallenge.fourworktimeapp.adapter.driven.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.fiap.techchallenge.fourworktimeapp.adapter.driven.data")
public class SpringJpaConfig {
}