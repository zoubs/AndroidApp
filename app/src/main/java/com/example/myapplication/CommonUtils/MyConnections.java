package com.example.myapplication.CommonUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnections {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        return DriverManager.getConnection("jdbc:jtds:sqlserver://39.101.211.144:1433/master;charset=utf8", "sa", "Chy19981215GC");
    }
}
