package com.jmc.apicontrole.Models.Classes;

import java.time.LocalDate;

public class Colmeia {
    private int id;
    private String grauDeLimpeza;
    private String tipoDeColmeia;
    private String especie;
    private String produtividade;
    private String agressividade;
    private int quantidadeDeMelgueiras;
    private LocalDate dataDeInstalacao;
    private String statusDaColmeia;
    private String observacoesAdicionais;
    private int apiarioId;

    public Colmeia() {

    }

    public Colmeia(int id, String grauDeLimpeza, String tipoDeColmeia, String especie, String produtividade,
                   String agressividade, int quantidadeDeMelgueiras, LocalDate dataDeInstalacao, String statusDaColmeia,
                   String observacoesAdicionais, int apiarioId) {
        this.id = id;
        this.grauDeLimpeza = grauDeLimpeza;
        this.tipoDeColmeia = tipoDeColmeia;
        this.especie = especie;
        this.produtividade = produtividade;
        this.agressividade = agressividade;
        this.quantidadeDeMelgueiras = quantidadeDeMelgueiras;
        this.dataDeInstalacao = dataDeInstalacao;
        this.statusDaColmeia = statusDaColmeia;
        this.observacoesAdicionais = observacoesAdicionais;
        this.apiarioId = apiarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrauDeLimpeza() {
        return grauDeLimpeza;
    }

    public void setGrauDeLimpeza(String grauDeLimpeza) {
        this.grauDeLimpeza = grauDeLimpeza;
    }

    public String getTipoDeColmeia() {
        return tipoDeColmeia;
    }

    public void setTipoDeColmeia(String tipoDeColmeia) {
        this.tipoDeColmeia = tipoDeColmeia;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getProdutividade() {
        return produtividade;
    }

    public void setProdutividade(String produtividade) {
        this.produtividade = produtividade;
    }

    public String getAgressividade() {
        return agressividade;
    }

    public void setAgressividade(String agressividade) {
        this.agressividade = agressividade;
    }

    public int getQuantidadeDeMelgueiras() {
        return quantidadeDeMelgueiras;
    }

    public void setQuantidadeDeMelgueiras(int quantidadeDeMelgueiras) {
        this.quantidadeDeMelgueiras = quantidadeDeMelgueiras;
    }

    public LocalDate getDataDeInstalacao() {
        return dataDeInstalacao;
    }

    public void setDataDeInstalacao(LocalDate dataDeInstalacao) {
        this.dataDeInstalacao = dataDeInstalacao;
    }

    public String getStatusDaColmeia() {
        return statusDaColmeia;
    }

    public void setStatusDaColmeia(String statusDaColmeia) {
        this.statusDaColmeia = statusDaColmeia;
    }

    public String getObservacoesAdicionais() {
        return observacoesAdicionais;
    }

    public void setObservacoesAdicionais(String observacoesAdicionais) {
        this.observacoesAdicionais = observacoesAdicionais;
    }

    public int getApiarioId() {
        return apiarioId;
    }

    public void setApiarioId(int apiarioId) {
        this.apiarioId = apiarioId;
    }
}
