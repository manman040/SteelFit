package com.kelasandroidappsirhafizee.steelfit;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionclass {

    String classes = "net.sourceforge.jtds.jdbc.Driver";
    protected static String ip = "10.0.2.2";

    protected static String port = "1433";
    protected static String db = "SteelFit";
    protected static String us = "sa";
    protected static String password = "9919";

    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            Class.forName(classes);
            String conUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + db;
            conn = DriverManager.getConnection(conUrl, us, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;


    }
}

    /// ADA SOMETHING LAGI DEKAT SINI KOT
