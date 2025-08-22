package com.example.siscomex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DadosRecepcao {
    // Defina os campos conforme a documentação da API
    private String campoExemplo;
    private Integer outroCampo;

    // Getters e Setters
    public String getCampoExemplo() {
        return campoExemplo;
    }

    public void setCampoExemplo(String campoExemplo) {
        this.campoExemplo = campoExemplo;
    }

    public Integer getOutroCampo() {
        return outroCampo;
    }

    public void setOutroCampo(Integer outroCampo) {
        this.outroCampo = outroCampo;
    }
}