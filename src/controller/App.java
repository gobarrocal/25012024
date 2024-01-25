package controller;
import view.*;
import model.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Bem vindo a padoka mais deliciosa do mundo!");
        Cardapio.exibirCardapio();
        System.out.println(InterfacePadoka.varInt);
        InterfacePadoka.varInt = 3;
        System.out.println(InterfacePadoka.varInt);
    }
}

