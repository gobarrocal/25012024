package view;
import java.sql.*;
import java.util.*;

import controller.*;
import model.*;
public class Receita {
    public static void exibirCardapio() {
        System.out.println("Veja as opções abaixo e digite o número da opção desejada, depois tecle enter:");

        try {
            Scanner scanner = new Scanner(System.in);

            Connection conn = MySQLConnector.conectar();
            String strSqlSelectReceitas = "select * from `db_mysql_connector`.`tbl_receitas`;";
            Statement stmSqlReceitas = conn.createStatement();
            ResultSet rsSqlReceitas = stmSqlReceitas.executeQuery(strSqlSelectReceitas);
            

            int numReceita = 1;

            while (rsSqlReceitas.next()) {
                String idReceita = rsSqlReceitas.getString("id");
                String nomeReceita = rsSqlReceitas.getString("nome");
                String preparoReceita = rsSqlReceitas.getString("preparo");

                // Restante do código...

                if (receitaDisponivel) {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - disponível.");
                } else {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - indisponível.");
                }
                numReceita++;
            }

            System.out.println("Digite o número da receita desejada:");
            int numeroPedido = scanner.nextInt();

            // Validar o número da receita
            if (numeroPedido >= 1 && numeroPedido <= numReceita) {
                // Processar o pedido conforme necessário
                System.out.println("Você escolheu a receita " + numeroPedido + ".");
                // Implemente a lógica para processar a receita escolhida aqui...

            } else {
                System.out.println("Número de receita inválido. Por favor, escolha uma opção válida.");
            }

            // Fechar recursos
            scanner.close();
            stmSqlReceitas.close();
            rsSqlReceitas.close();
        } catch (Exception e) {
            System.out.println("Ops! Ocorreu o erro " + e);
        }
    }
}