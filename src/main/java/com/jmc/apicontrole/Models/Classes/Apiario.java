package com.jmc.apicontrole.Models.Classes;

import java.time.LocalDate;

public class Apiario {
    private int apiarioId;
    private int apicultorId;
    private String apicultorNome;
    private String municipio;
    private String uf;
    private String distrito;
    private String pontoDeReferencia;
    private int quantidadeDeColmeias;
    private String tipoDeApiario;
    private LocalDate dataDeInicio;
    private String tipoDeVegetacao;
    private double areaTotal;
    private String presencaDeMataNativa;
    private String presencaDePomar;
    private double producaoTotal;

    public Apiario() {

    }

    public Apiario(int apiarioId, int apicultorId) {
        this.apiarioId = apiarioId;
        this.apicultorId = apicultorId;
    }

    public Apiario(int apiarioId, int apicultorId, String apicultorNome, String municipio, String uf, String distrito,
                   String pontoDeReferencia, int quantidadeDeColmeias, String tipoDeApiario, LocalDate dataDeInicio,
                   String tipoDeVegetacao, double areaTotal, String presencaDeMataNativa, String presencaDePomar,
                   double producaoTotal) {
        this.apiarioId = apiarioId;
        this.apicultorId = apicultorId;
        this.apicultorNome = apicultorNome;
        this.municipio = municipio;
        this.uf = uf;
        this.distrito = distrito;
        this.pontoDeReferencia = pontoDeReferencia;
        this.quantidadeDeColmeias = quantidadeDeColmeias;
        this.tipoDeApiario = tipoDeApiario;
        this.dataDeInicio = dataDeInicio;
        this.tipoDeVegetacao = tipoDeVegetacao;
        this.areaTotal = areaTotal;
        this.presencaDeMataNativa = presencaDeMataNativa;
        this.presencaDePomar = presencaDePomar;
        this.producaoTotal = producaoTotal;
    }

    public double getProducaoTotal() {
        return producaoTotal;
    }

    public void setProducaoTotal(double producaoTotal) {
        this.producaoTotal = producaoTotal;
    }

    public String getPresencaDePomar() {
        return presencaDePomar;
    }

    public void setPresencaDePomar(String presencaDePomar) {
        this.presencaDePomar = presencaDePomar;
    }

    public String getPresencaDeMataNativa() {
        return presencaDeMataNativa;
    }

    public void setPresencaDeMataNativa(String presencaDeMataNativa) {
        this.presencaDeMataNativa = presencaDeMataNativa;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getTipoDeVegetacao() {
        return tipoDeVegetacao;
    }

    public void setTipoDeVegetacao(String tipoDeVegetacao) {
        this.tipoDeVegetacao = tipoDeVegetacao;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public String getTipoDeApiario() {
        return tipoDeApiario;
    }

    public void setTipoDeApiario(String tipoDeApiario) {
        this.tipoDeApiario = tipoDeApiario;
    }

    public int getQuantidadeDeColmeias() {
        return quantidadeDeColmeias;
    }

    public void setQuantidadeDeColmeias(int quantidadeDeColmeias) {
        this.quantidadeDeColmeias = quantidadeDeColmeias;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }

    public void setPontoDeReferencia(String pontoDeReferencia) {
        this.pontoDeReferencia = pontoDeReferencia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getApicultorNome() {
        return apicultorNome;
    }

    public void setApicultorNome(String apicultorNome) {
        this.apicultorNome = apicultorNome;
    }

    public int getApicultorId() {
        return apicultorId;
    }

    public void setApicultorId(int apicultorId) {
        this.apicultorId = apicultorId;
    }

    public int getApiarioId() {
        return apiarioId;
    }

    public void setApiarioId(int apiarioId) {
        this.apiarioId = apiarioId;
    }
}
