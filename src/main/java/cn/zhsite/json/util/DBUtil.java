package cn.zhsite.json.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static Connection connection;

    static{
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "hostel";
        String password = "linzuhua";
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/" + dbName;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, userName,
                    password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String query(String sql){

        try {
            return JSONUtil.toJSON(connection.createStatement().executeQuery(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("exception in sql:"+sql);
        return "";
    }

    public static String update(String sql){

        try {
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("exception in sql:"+sql);
        return "";
    }
    public static void main(String[] args){
        System.out.println(query("select * from course"));
    }
}
