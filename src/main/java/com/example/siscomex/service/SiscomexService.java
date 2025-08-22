package com.example.siscomex.service;

import com.example.siscomex.client.SiscomexConsultaClient;
import com.example.siscomex.client.SiscomexRecepcaoClient;
import com.example.siscomex.model.DadosRecepcao;
import com.example.siscomex.model.RespostaConsulta;
import com.example.siscomex.model.RespostaRecepcao;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SiscomexService {

    private final AuthService authService;
    private final SiscomexRecepcaoClient recepcaoClient;
    private final SiscomexConsultaClient consultaClient;

    public SiscomexService(AuthService authService,
                           SiscomexRecepcaoClient recepcaoClient,
                           SiscomexConsultaClient consultaClient) {
        this.authService = authService;
        this.recepcaoClient = recepcaoClient;
        this.consultaClient = consultaClient;
    }

    public RespostaConsulta processarDados(DadosRecepcao dados) {
        // Obter token de acesso
        String accessToken = authService.getAccessToken();

        // Enviar dados para recepção
        RespostaRecepcao respostaRecepcao = recepcaoClient.enviarDados(dados, accessToken);
        String idTransmissao = respostaRecepcao.getIdTransmissao();

        // Consultar status do processamento periodicamente
        RespostaConsulta respostaConsulta;
        do {
            try {
                TimeUnit.SECONDS.sleep(10); // Aguardar 10 segundos entre consultas
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Processamento interrompido", e);
            }

            respostaConsulta = consultaClient.consultarProcessamento(idTransmissao, accessToken);

        } while ("EM_PROCESSAMENTO".equals(respostaConsulta.getSituacao().getCodigo()));

        return respostaConsulta;
    }
}