package com.jmc.apicontrole.Models;

public class teste {
    public static void main(String[] args) {
        DatabaseDriver dbDriver = new DatabaseDriver();

        if(dbDriver.isConnectionValid()){
            System.out.println("Conexao com o banco valida");

            String table_name = "colheita";

            dbDriver.displayTableData(table_name);
            String teste = dbDriver.getColumn("user", "username", 1);
            System.out.println(teste);
            dbDriver.setColumn("user", "username", 1, "lucas");
            teste = dbDriver.getColumn("user", "username", 1);
            System.out.println(teste);

            boolean existe = dbDriver.valueExists("user", "username", "lucas");
            System.out.println(existe);

            String[] lista = dbDriver.getAllColumnValues("colheita", "id");

            for (String s : lista) {
                System.out.println(s);
            }
        }else{
            System.out.println("Falha na conexao");
        }
    }
}
