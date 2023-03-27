package com.mycompany.estudo.jdbc;


import DAO.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EstudoJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UsuarioDAO user= new UsuarioDAO();
        
        boolean fim= true;
        do{
            int opcao=Integer.parseInt(JOptionPane.showInputDialog("""
                                        "escolha uma das opções:"                           
                                        1- listar usuario
                                        2- cadastrar usuario
                                        3- deletar usuario
                                        4- sair
                                        """));
            
            switch(opcao){
                case 1 ->{
                    //listar os usuarios:
                    System.out.println("------USUARIOS:------");
                    ArrayList<Usuario> usuarios= user.listaUsuarios();
            
                    usuarios.stream().forEach(usuario -> System.out.println(usuario.toString()));
                }
                case 2 ->{
                    //cadastrar usuario:
                    String nome= JOptionPane.showInputDialog(null, "digite seu nome:");
            
                    String idade= JOptionPane.showInputDialog(null,"digite sua idade:");
        
                    System.out.println("cadastro realizado");
        
                    Usuario novoUsuario= new Usuario(2,nome,Integer.parseInt(idade));
        
                    user.addUsuario(novoUsuario);      
                }
                case 3 ->{
                    //deletar usuario
                    
                    int idUser = Integer.parseInt(JOptionPane.showInputDialog("digite o id do usuario"));
                    
                    user.deleteUser(idUser);
                    
                    System.out.println("Usuario deletado com sucesso!");
                }
                case 4 ->{
                    System.out.println("fim!");
                    fim= false;
                }
                
            }
        }while(fim!=false);
        
    }
}
