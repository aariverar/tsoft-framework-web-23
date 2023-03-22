package com.tsoft.dao;

import com.tsoft.base.Report;
import com.tsoft.base.Utils;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MPI20v2 {

    private Conexion conexion;

    public MPI20v2(){
        conexion = new Conexion("ecommerce");
    }

    public void validarOperacion(Map<String, String> data){
        String step = "Consultando y validando DBA Autentificación (MPI)";

        //String Merchant = data.get(p1m.MERCHANTID).trim();
        //String Purchase = data.get(p1m.PURCHASE).trim();
        String Token = data.get("Token").trim();

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM mpi20v2 m ");
        //query.append("WHERE m.merchantId='" + Merchant + "' AND ");
        //query.append("m.purchaseNumber='"+Purchase + "' AND ");
        query.append("m.orderId ='"+Token+"'");

        Utils.println(query.toString());

        try {
            PreparedStatement preparedStatement = conexion.conectar().prepareStatement(query.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                JSONObject response = new JSONObject();
                response.put("referenceId", resultSet.getString("referenceId"));
                response.put("orderId", resultSet.getString("orderId"));
                response.put("merchantId", resultSet.getString("merchantId"));
                response.put("sessionKey", resultSet.getString("sessionKey"));
                response.put("purchaseNumber", resultSet.getString("purchaseNumber"));
                response.put("cardNumber", resultSet.getString("cardNumber"));
                response.put("checkRequestId", resultSet.getString("checkRequestId"));

                Utils.printJSON(response);

                Report.onPass(null, step);
                Report.onPass(null, Utils.prettyJSON(response.toJSONString()));
            }else{
                Report.onFail(null, step);
            }
            conexion.desconectar();
        } catch (SQLException e) {
            Report.onFail(null, step);
            throw new RuntimeException(e);
        }
    }

}
