package com.example.siscomex.controller;

import com.example.siscomex.model.DadosRecepcao;
import com.example.siscomex.model.RespostaConsulta;
import com.example.siscomex.service.SiscomexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/siscomex")
public class SiscomexController {

    private final SiscomexService siscomexService;

    public SiscomexController(SiscomexService siscomexService) {
        this.siscomexService = siscomexService;
    }

    @PostMapping("/processar")
    public ResponseEntity<RespostaConsulta> processarDados(@RequestBody DadosRecepcao dados) {
        try {
            RespostaConsulta resposta = siscomexService.processarDados(dados);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}