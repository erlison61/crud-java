package com.mycompany.estudo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    private String driver,severName,myDataBase;
    private String urlConnection,root,password;
    private Connection con;
    
    public ConnectionJDBC() {
        this.driver="org.postgresql.Driver";
        this.severName="localhost";
        this.myDataBase="test";
        this.root="postgres";
        this.password="post";
        this.urlConnection="jdbc:postgresql://"+this.severName+"/"+this.myDataBase;
        this.con=null;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    

    public Connection factoryConnection() throws SQLException, ClassNotFoundException{
        try{
            
           Class.forName(getDriver());
           this.con=DriverManager.getConnection(getUrlConnection(),getRoot(),getPassword());
           return getCon();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("erro na conexão com mySQL:"+e.getMessage());
            
            return null;
        } 
    }
    
    public boolean closeConnection() throws ClassNotFoundException{
          try {
            con.close();
            return true;
        } catch (SQLException var1) {
            System.out.println("erro ao fechar conexão"+ var1.getMessage());
            return false;
        }
    }

}


