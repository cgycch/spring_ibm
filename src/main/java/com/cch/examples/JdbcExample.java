package com.cch.examples;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcExample {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test_demo?useUnicode=true&characterEncoding=utf-8";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static void main(String[] args) {
		JdbcExample example = new JdbcExample();
		example.JdbcTransfer();
	}

	public void JdbcTransfer() {
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute("update t_account set amount = amount - 50 where account_id = 'A'");
			stmt.execute("update t_account set amount1 = amount + 50 where account_id = 'B'");
			conn.commit();
			System.out.println("commit success");
		} catch (SQLException | ClassNotFoundException sqle) {
			try {
				conn.rollback();
				System.err.println("rollback success");
				stmt.close();
				conn.close();
			} catch (Exception ignore) {
				System.err.println("close is error");
			}
			sqle.printStackTrace();
		}
	}

}
