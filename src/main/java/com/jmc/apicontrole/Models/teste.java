package com.jmc.apicontrole.Models;

public class teste {
    public static void main(String[] args) {
        DatabaseDriver dbDriver = new DatabaseDriver();

        if(dbDriver.isConnectionValid()){
            System.out.println("Conexao com o banco valida");

            String table_name = "colheita";

            dbDriver.displayTableData(table_name);
        }else{
            System.out.println("Falha na conexao");
        }
    }
}
