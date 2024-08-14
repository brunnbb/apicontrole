package com.jmc.apicontrole.Models.Classes;

import java.time.LocalDate;

public class Evento {
    private int id;
    private int apiarioId;
    private LocalDate data;
    private String descricao;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getApiarioId(){
        return apiarioId;
    }

    public void setApiarioId(int apiarioId){
        this.apiarioId = apiarioId;
    }

    public LocalDate getData(){
        return data;
    }

    public void setData(LocalDate data){
        this.data = data;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
