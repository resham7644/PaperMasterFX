package com.example.papermasterfx.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlDbConnection {
    public static Connection getMySQLConnection()
    {
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/papermaster","resham","resham123");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return con;
    }
}
