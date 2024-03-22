package com.fiap.techchallenge.fourworktimeapp.application.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {
    
    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private SecurityContext securityContext;

    @Spy
    private ObjectMapper objectMapper;
    
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Test
    void shouldDoFilterInternalWithoutTokenAndProceed() throws Exception {
        // Arrange
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        request.setSession(new MockHttpSession(null, "mockSession"));

        // Act
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldDoFilterInternalWithInvalidTokenAndBlockWith403() throws Exception {
        // Arrange
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        var token = "invalidToken";

        request.setSession(new MockHttpSession(null, "mockSession"));
        request.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "authentication error");
        errorDetails.put("details", "invalid token");

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.resolveClaims(request)).thenReturn(null);

        var filterChain = new MockFilterChain();

        // Act
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertEquals(403, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals(objectMapper.writeValueAsString(errorDetails), response.getContentAsString());
    }

    @Test
    void shouldDoFilterInternalWithValidTokenAndProceed() throws Exception {
        // Arrange
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        
        String token = "validToken";
        String subject = "user1";

        request.setSession(new MockHttpSession(null, "mockSession"));
        request.addHeader("Authorization", "Bearer " + token);

        Claims claims = Jwts.claims().subject(subject).build();

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.resolveClaims(request)).thenReturn(claims);
        when(jwtUtil.validateClaims(claims)).thenReturn(true);

        var filterChain = new MockFilterChain();

        // Act
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject, "", new ArrayList<>());

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }
}