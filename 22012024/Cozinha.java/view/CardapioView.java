package view;
import java.sql.*;
import java.util.*;

import controller.*;
import model.*;

public class CardapioView {
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
                boolean receitaDisponivel = true;
                String idReceita = rsSqlReceitas.getString("id");
                String nomeReceita = rsSqlReceitas.getString("nome");
                String preparoReceita = rsSqlReceitas.getString("preparo");
                
                String strSqlSelectItensReceita = "select * from `db_mysql_connector`.`tbl_itens_receita` where `id_receita` = " + idReceita + ";";
                Statement stmSqlItensReceita = conn.createStatement();
                ResultSet rsSqlItensReceitas = stmSqlItensReceita.executeQuery(strSqlSelectItensReceita);
                
                while (rsSqlItensReceitas.next()) {
                    String idProdutoReceita = rsSqlItensReceitas.getString("id_produto");
                    String qtdProdutoReceita = rsSqlItensReceitas.getString("qtd");
                    String strSqlSelectProdutosReceita = "select * from `db_mysql_connector`.`tbl_produtos` where `id` = " + idProdutoReceita;
                    Statement stmSqlProdutosReceita = conn.createStatement();
                    ResultSet rsSqlProdutosReceita = stmSqlProdutosReceita.executeQuery(strSqlSelectProdutosReceita);
                    
                    while (rsSqlProdutosReceita.next()) {
                        String qtdProduto = rsSqlProdutosReceita.getString("qtd");
                        int qtdProdutoTotal = Integer.valueOf(qtdProduto);
                        int qtdProdutoAtual = Integer.valueOf(qtdProdutoReceita);
                        
                        if (qtdProdutoTotal < qtdProdutoAtual) {
                            receitaDisponivel = false;
                        }
                    }
                    stmSqlProdutosReceita.close();
                    rsSqlProdutosReceita.close();
                }

                if (receitaDisponivel) {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - disponível.");
                } else {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - indisponível.");
                }
                
                numReceita++;
            }

            System.out.println("Digite o número da receita desejada:");
            int numeroPedido = scanner.nextInt();

            scanner.close();
            stmSqlReceitas.close();
            rsSqlReceitas.close();
        } catch (Exception e) {
            System.out.println("Ops! Ocorreu o erro " + e);
        }
    }
}
