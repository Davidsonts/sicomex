package com.example.siscomex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespostaConsulta {
    @JsonProperty("idTransmissao")
    private String idTransmissao;

    @JsonProperty("situacao")
    private Situacao situacao;

    @JsonProperty("dadosProcessamento")
    private Object dadosProcessamento; // Ajuste conforme a estrutura real

    // Getters e Setters
    public String getIdTransmissao() {
        return idTransmissao;
    }

    public void setIdTransmissao(String idTransmissao) {
        this.idTransmissao = idTransmissao;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Object getDadosProcessamento() {
        return dadosProcessamento;
    }

    public void setDadosProcessamento(Object dadosProcessamento) {
        this.dadosProcessamento = dadosProcessamento;
    }

    public static class Situacao {
        @JsonProperty("codigo")
        private String codigo;

        @JsonProperty("descricao")
        private String descricao;

        // Getters e Setters
        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }
}