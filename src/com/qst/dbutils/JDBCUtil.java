package com.qst.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

	// 数据库驱动类?
	private static final String Driver = "com.mysql.jdbc.Driver";
	// 数据库地?
	private static final String url = "jdbc:mysql://localhost:3306/usermanager?useUnicode=true&characterEncoding=utf8";
	// 用户
	private static final String user = "root";
	// 密码
	private static final String pwd = "509165";

	static Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	/**
	 * 连接数据?
	 * @return
	 */
	public static Connection getConnection() {
		try {
			// 虚拟机装载类
			Class.forName(Driver);
			// 连接数据?
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("连接成功");
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			System.out.println("未发现驱动包");
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("用户名或密码出错");
		}
		return conn;
	}
	
	public static void main(String[] args) throws SQLException {
		Connection con = getConnection();
		String sql = "SELECT * FROM t_user u WHERE u.status = ?";
		PreparedStatement psm = con.prepareStatement(sql);
		// 前面的参数代表？的位置，后面的值，代表传进去的值
		psm.setInt(1, 1);
		ResultSet rs = psm.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1)+"   " + rs.getString(2) +"   "+rs.getString(3));
		}
	}

	public static void close(Connection conn, Statement stmt,
			PreparedStatement psmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
	}

	/*
	* 查询操作*/
	public ResultSet executeQuery(String sql, Object[] param) {
		try{
			pstmt = getConnection().prepareStatement(sql);
			if (param != null){
				for (int i = 0;i<param.length;i++){
					pstmt.setObject(i+1,param[i]);
				}
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
    /*执行SQL语句执行增删改操作，不能执行查询操作*/
	public int executeUpdate(String sql, Object[] param) {
		int num = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			if(param != null){
				for (int i = 0;i<param.length;i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			num = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
}
