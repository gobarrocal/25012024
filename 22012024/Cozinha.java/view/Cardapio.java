package view;
import java.sql.*;
import java.util.*;

import controller.*;
import model.*;

public class Cardapio {
    public static void exibirCardapio() {
        System.out.println("Veja as opções abaixo e digite o número da opção desejada, depois tecle enter:");
        
        try {

            Scanner scanner = new Scanner(System.in);
    
            System.out.println("Até aqui ok");
            Connection conn = MySQLConnector.conectar();
            String strSqlSelectReceitas = "select * from `db_mysql_connector`.`tbl_receitas`;";
            Statement stmSqlReceitas = conn.createStatement();
            ResultSet rsSqlReceitas = stmSqlReceitas.executeQuery(strSqlSelectReceitas);

            String receitas = "";
            String idReceita = "";
            String nomeReceita = "";
            String preparoReceita = "";
            String strSqlSelectItensReceita = "";
            Statement stmSqlItensReceita = null;
            ResultSet rsSqlItensReceitas = null;

            String idProdutoReceita = "";
            String qtdProdutoReceita = "";
            String strSqlSelectProdutosReceita = "";
            Statement stmSqlProdutosReceita = null;
            ResultSet rsSqlProdutosReceita = null;

            String idProduto = "";
            String qtdProduto = "";
            String strSqlSelectProdutos = "";
            Statement stmSqlProdutos = null;
            ResultSet rsSqlProdutos = null;

            int qtdProdutoTotal = 0;
            int qtdProdutoAtual = 0;
            boolean receitaDisponivel = true;
            int numReceita = 1;

            

            while (rsSqlReceitas.next()) {
                receitaDisponivel = true;
                idReceita = rsSqlReceitas.getString("id");
                nomeReceita = rsSqlReceitas.getString("nome");
                preparoReceita = rsSqlReceitas.getString("preparo");
                strSqlSelectItensReceita = "select * from `db_mysql_connector`.`tbl_itens_receita` where `id_receita` = " + idReceita + ";";
                stmSqlItensReceita = conn.createStatement();
                rsSqlItensReceitas = stmSqlItensReceita.executeQuery(strSqlSelectItensReceita);
                while (rsSqlItensReceitas.next()) {
                    idProdutoReceita = rsSqlItensReceitas.getString("id_produto");
                    qtdProdutoReceita = rsSqlItensReceitas.getString("qtd");
                    strSqlSelectProdutosReceita = "select * from `db_mysql_connector`.`tbl_produtos` where `id` = " + idProdutoReceita;
                    stmSqlProdutosReceita = conn.createStatement();
                    rsSqlProdutosReceita = stmSqlProdutosReceita.executeQuery(strSqlSelectProdutosReceita);
                    while (rsSqlProdutosReceita.next()) {
                        qtdProduto = rsSqlProdutosReceita.getString("qtd");
                        qtdProdutoTotal = Integer.valueOf(qtdProduto);
                        qtdProdutoAtual = Integer.valueOf(qtdProdutoReceita);
                        if (qtdProdutoTotal < qtdProdutoAtual) {
                            receitaDisponivel = false;
                        }
                    }
                    stmSqlProdutosReceita.close();
                    rsSqlProdutosReceita.close();
                }
                if (receitaDisponivel == true) {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - disponível.");
                } else {
                    System.out.println("[" + numReceita + "] - " + nomeReceita + " - indisponível.");
                }
                System.out.println("Digite o número da receita desejada:");
                int numeroPedido = scanner.nextInt();
                numReceita ++;
                stmSqlItensReceita.close();
                rsSqlItensReceitas.close();
            }
            stmSqlReceitas.close();
            rsSqlReceitas.close();
        } catch (Exception e) {
            System.out.println("Ops! Ocorreu o erro " + e);
        }
    }
}