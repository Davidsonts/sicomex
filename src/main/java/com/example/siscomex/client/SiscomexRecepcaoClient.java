package com.example.siscomex.client;

import com.example.siscomex.model.DadosRecepcao;
import com.example.siscomex.model.RespostaRecepcao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SiscomexRecepcaoClient {

    private final RestTemplate restTemplate;

    @Value("${siscomex.recepcao.url}")
    private String recepcaoUrl;

    public SiscomexRecepcaoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RespostaRecepcao enviarDados(DadosRecepcao dados, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<DadosRecepcao> entity = new HttpEntity<>(dados, headers);

        ResponseEntity<RespostaRecepcao> response = restTemplate.exchange(
                recepcaoUrl,
                HttpMethod.POST,
                entity,
                RespostaRecepcao.class
        );

        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Falha no envio para recepção: " + response.getStatusCode());
        }
    }
}