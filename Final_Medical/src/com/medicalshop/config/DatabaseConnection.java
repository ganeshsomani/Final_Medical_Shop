package com.medicalshop.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_shop", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
