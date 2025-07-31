package com.example.papermasterfx.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

class Curd{
    Connection con;
    Curd()
    {
        con = MySqlDbConnection.getMySQLConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }
    }
    void doSave()
    {
        String query = "insert into newspapers values(?,?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,"The Tribune Punjabi");
            pst.setString(2,"Punjabi");
            pst.setFloat(3,5.5f);
            pst.executeUpdate();
            System.out.println("Record Saved");

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
public class JdbcCurd {
    public static void main(String[] args) {
        Curd obj = new Curd();
        obj.doSave();
    }
}
