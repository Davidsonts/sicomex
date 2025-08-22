package com.example.siscomex.service;

import com.example.siscomex.client.SiscomexConsultaClient;
import com.example.siscomex.client.SiscomexRecepcaoClient;
import com.example.siscomex.model.DadosRecepcao;
import com.example.siscomex.model.RespostaConsulta;
import com.example.siscomex.model.RespostaRecepcao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SiscomexService {

    private static final Logger logger = LoggerFactory.getLogger(SiscomexService.class);

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
        try {
            // Obter token de acesso
            logger.info("Obtendo token de acesso...");
            String accessToken = authService.getAccessToken();

            // Enviar dados para recepção
            logger.info("Enviando dados para recepção...");
            RespostaRecepcao respostaRecepcao = recepcaoClient.enviarDados(dados, accessToken);
            String idTransmissao = respostaRecepcao.getIdTransmissao();
            logger.info("ID de transmissão recebido: {}", idTransmissao);

            // Consultar status do processamento periodicamente
            RespostaConsulta respostaConsulta;
            int tentativas = 0;
            int maxTentativas = 30; // Máximo de 30 tentativas (5 minutos)

            do {
                tentativas++;
                logger.info("Consultando status (tentativa {})...", tentativas);

                try {
                    TimeUnit.SECONDS.sleep(10); // Aguardar 10 segundos entre consultas
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Processamento interrompido", e);
                }

                respostaConsulta = consultaClient.consultarProcessamento(idTransmissao, accessToken);
                logger.info("Status atual: {}", respostaConsulta.getSituacao().getCodigo());

                if (tentativas >= maxTentativas) {
                    throw new RuntimeException("Número máximo de tentativas excedido. Processamento ainda em andamento.");
                }

            } while ("EM_PROCESSAMENTO".equals(respostaConsulta.getSituacao().getCodigo()));

            logger.info("Processamento concluído com status: {}", respostaConsulta.getSituacao().getCodigo());
            return respostaConsulta;

        } catch (Exception e) {
            logger.error("Erro ao processar dados: {}", e.getMessage(), e);
            throw new RuntimeException("Falha no processamento: " + e.getMessage(), e);
        }
    }
}