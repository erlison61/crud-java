package com.mycompany.estudo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    private ArrayList<Usuario> usuarios;
    private ConnectionJDBC conexao;
    private String selected_users_sql;
    private String insert_user_sql;
    
    
    public UsuarioDAO() {
        this.usuarios = new ArrayList<>();
        this.conexao= new ConnectionJDBC();
        this.selected_users_sql= "select * from usuario";
        this.insert_user_sql="insert into usuario(nome, idade) values(?,?)";
    }
    
    
    public ArrayList<Usuario> listaUsuarios() throws ClassNotFoundException {
        
        try{
            Connection con= this.conexao.factoryConnection();
             
            PreparedStatement stmt= con.prepareStatement(this.selected_users_sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nome= rs.getString("nome");
                int idade= rs.getInt("idade");
            
                this.usuarios.add(new Usuario(id,nome,idade));
            }
            return usuarios;
        } catch (SQLException ex) {
            System.out.println("falha ao listar usuarios "+ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("classe não encontrada"+ex.getMessage());
            return null;
        }finally{
            this.conexao.closeConnection();
        }
      
    }
    
    public boolean addUsuario(Usuario user) throws ClassNotFoundException{
        try (Connection con= this.conexao.factoryConnection();                     
            PreparedStatement stmt=con.prepareStatement(this.insert_user_sql)){
            
            stmt.setString(1, user.getNome());
            stmt.setInt(2, user.getIdade());
            
            con.setAutoCommit(false);//inicia a transação
            stmt.executeUpdate();
            con.commit();//confirma a transação
            
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("erro"+ex.getMessage());
            return false;
        }finally{
            this.conexao.closeConnection();
        }
       
        
    }
    
    
    
    
}
