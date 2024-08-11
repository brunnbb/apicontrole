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

            dbDriver.deleteRow("colheita", 64);
            //dbDriver.createUser("luna", "senha123");
            dbDriver.getColumn("user", "username", 2);

            //dbDriver.createApiario(1, "São Paulo", "SP", "Centro", "Próximo ao parque", 10,
                    //"Comercial", "2024-08-10", "Mata Atlântica", 500.0,
                    //"Sim", "Não", 1000.0);

            //dbDriver.createHive("Limpo", "Langstroth", "Apis mellifera", "Alta","Baixa", 5, "2024-08-10", "Ativa","Observação teste", 1);

            dbDriver.createColheita(1, "2024-08-10", 50.0);

        }else{
            System.out.println("Falha na conexao");
        }
    }
}
