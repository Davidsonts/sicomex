package com.example.siscomex.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    @Value("${siscomex.auth.url}")
    private String authUrl;

    @Value("${siscomex.client.id}")
    private String clientId;

    @Value("${siscomex.client.secret}")
    private String clientSecret;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<AuthResponse> response = restTemplate.exchange(
                    authUrl,
                    HttpMethod.POST,
                    entity,
                    AuthResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getAccessToken();
            } else {
                throw new RuntimeException("Falha na autenticação: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter token de acesso: " + e.getMessage(), e);
        }
    }

    private static class AuthResponse {
        private String access_token;
        private String token_type;
        private int expires_in;

        public String getAccessToken() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }
}