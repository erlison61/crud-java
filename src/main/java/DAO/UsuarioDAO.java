package DAO;

import conexão.ConnectionJDBC;
import com.mycompany.estudo.jdbc.Usuario;
import interfaces.UsuarioDAOInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements UsuarioDAOInterface{
    private ArrayList<Usuario> usuarios;
    private ConnectionJDBC conexao;
    private String selected_users_sql;
    private String insert_user_sql;
    private String delete_user_sql;
    
    
    public UsuarioDAO() {
        this.usuarios = new ArrayList<>();
        this.conexao= new ConnectionJDBC();
        this.selected_users_sql= "select * from usuario";
        this.insert_user_sql="insert into usuario(nome, idade) values(?,?)";
        this.delete_user_sql="delete from usuario where id = ?";
                
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ConnectionJDBC getConexao() {
        return conexao;
    }

    public void setConexao(ConnectionJDBC conexao) {
        this.conexao = conexao;
    }

    public String getSelected_users_sql() {
        return selected_users_sql;
    }

    public void setSelected_users_sql(String selected_users_sql) {
        this.selected_users_sql = selected_users_sql;
    }

    public String getInsert_user_sql() {
        return insert_user_sql;
    }

    public void setInsert_user_sql(String insert_user_sql) {
        this.insert_user_sql = insert_user_sql;
    }

    public String getDelete_user_sql() {
        return delete_user_sql;
    }

    public void setDelete_user_sql(String delete_user_sql) {
        this.delete_user_sql = delete_user_sql;
    }

    @Override
    public ArrayList<Usuario> listaUsuarios() {
        try{
            Connection con= getConexao().factoryConnection();
            
            PreparedStatement stmt= con.prepareStatement(getSelected_users_sql());
            
            ResultSet rs = stmt.executeQuery();
            
            // Limpa a lista "usuarios" antes de adicionar os novos usuários
            getUsuarios().clear();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nome= rs.getString("nome");
                int idade= rs.getInt("idade");
            
                getUsuarios().add(new Usuario(id,nome,idade));
            }
            return getUsuarios();
        } catch (SQLException ex) {
            System.out.println("falha ao listar usuarios "+ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("classe não encontrada"+ex.getMessage());
            return null;
        }finally{
            try {
                getConexao().closeConnection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean addUsuario(Usuario user) {
         try (Connection con= getConexao().factoryConnection();                     
            PreparedStatement stmt=con.prepareStatement(getInsert_user_sql())){
            
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
             try {
                 getConexao().closeConnection();
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
    }

    @Override
    public boolean deleteUser(int id) {
         //"delete from usuario where id = ?";
        try(Connection con= getConexao().factoryConnection();                     
            PreparedStatement stmt=con.prepareStatement(getDelete_user_sql())){
            
            stmt.setInt(1, id);
                    
            con.setAutoCommit(false);//inicia a transação
            stmt.executeUpdate();
            con.commit();//confirma a transação
            
            return true;
        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("erro"+ex.getMessage());
            return false;
        }finally{
            try {
                getConexao().closeConnection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
}
