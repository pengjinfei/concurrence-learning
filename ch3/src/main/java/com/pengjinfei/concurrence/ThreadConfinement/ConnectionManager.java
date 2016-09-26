package com.pengjinfei.concurrence.ThreadConfinement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 使用ThreaLocal来保证线程封闭性
 */
public class ConnectionManager {

    private static ThreadLocal<Connection> connectionHolder=new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection("jdbc:mysql");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
