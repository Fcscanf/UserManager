package com.qst.dbutils;

import java.sql.*;

public class DBUtil {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet resultSet = null;
    /*
    * 得到数据库连接
    * */

    public Connection getConnection() throws SQLException {
        String driver = Config.getValue("driver");
        String url = Config.getValue("url");
        String user = Config.getValue("user");
        String pwd = Config.getValue("pwd");
        try{
//            指定驱动程序
            Class.forName(driver);
//            建立数据库连接
            connection = DriverManager.getConnection(url,user,pwd);
            return connection;
        } catch (Exception e) {
//            如果在连接过程中出现异常,抛出异常信息
            throw new SQLException("驱动错误或连接失败！");
        }
    }
    /*
    * 释放资源*/
    public void closeAll(){
//        如果resultSet不为空，关闭resultSet
        if (resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        如果patmt不为空，关闭patmt
        if (pstmt != null){
            try{
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        如果connection不为空，关闭connection
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /*SQL查询*/
    public ResultSet execute(String preparedSql,String[] param){
//        处理SQL，执行SQL
        try{
//            得到PreparedStatement对象
            pstmt = connection.prepareStatement(preparedSql);
            if (param != null){
                for (int i = 0;i<param.length;i++){
//                    为预编译SQL设置参数
                    pstmt.setString(i + 1,param[i]);
                }
            }
//            执行SQL语句
            resultSet = pstmt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }
    /*执行SQL语句执行增删改查语句*/
    public int executeUpdate(String preparedSql,String[] param){
        int num = 0;
//        处理SQL，执行SQL
        try{
//            得到PreparedStatement对象
                    pstmt = connection.prepareStatement(preparedSql);
            if (param != null){
                for (int i = 0;i<param.length;i++){
//                    为预编译SQL设置参数
                    pstmt.setString(i + 1,param[i]);
                }
            }
//            执行SQL语句
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

}
