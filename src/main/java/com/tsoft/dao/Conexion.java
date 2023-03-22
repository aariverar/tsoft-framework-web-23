package com.tsoft.dao;

import com.tsoft.base.Utils;
import com.tsoft.utility.PropertiesFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    public Connection con;

    private String driver;
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;

    public Conexion(String database){
        PropertiesFile pf = new PropertiesFile();
        Properties properties = pf.getProperty();

        this.driver = properties.getProperty("db.driver");
        this.host = properties.getProperty("db.host");
        this.port = properties.getProperty("db.port");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.database = database;
    }

    public Connection conectar(){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(host + "/"+database, username, password);
            Utils.println("Conexión Establecido en la base de datos");
        } catch (ClassNotFoundException | SQLException ex){
            Utils.println("No se pudo establecer conexión con la base de datos");
        }
        return con;
    }

    public void desconectar(){
        try{
            con.close();
        } catch (SQLException ex){

        }
    }

}
