package com.example.siscomex.client;

import com.example.siscomex.model.RespostaConsulta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SiscomexConsultaClient {

    private final RestTemplate restTemplate;

    @Value("${siscomex.consulta.url}")
    private String consultaUrl;

    public SiscomexConsultaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RespostaConsulta consultarProcessamento(String idTransmissao, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = consultaUrl + "/" + idTransmissao;

        ResponseEntity<RespostaConsulta> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                RespostaConsulta.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Falha na consulta: " + response.getStatusCode());
        }
    }
}