package com.mibanco.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlite3Manager {

    public static void insertData(String scenarioName, String formattedDateTime, String time, String status, String framework, String hostname) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:\\\\Bmesrvc940\\auto_bd\\automatizador.db");
            stmt = conn.createStatement();
            String sql = "insert into automatizador (nombre_script,fecha_hora,timer,estado,framework, hostname) values ('" + scenarioName + "', '" + formattedDateTime + "', '" + time + "', '" + status + "', '" + framework + "', '" + hostname + "');";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
