package com.example.siscomex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespostaRecepcao {
    @JsonProperty("idTransmissao")
    private String idTransmissao;

    @JsonProperty("situacao")
    private Situacao situacao;

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