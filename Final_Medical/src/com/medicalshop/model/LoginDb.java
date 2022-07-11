package com.medicalshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.medicalshop.config.DatabaseConnection;

public class LoginDb {
	DatabaseConnection db = new DatabaseConnection();

	public LoginDb() {
		// Login log = new Login();
		//Login.main(null);
		Connection conn = db.getConnection();
		
		try {
			Long dataCount = 0L;
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'users'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dataCount = rs.getLong("counts");
			}
			rs.close();
			ps.close();
			if (dataCount == 0) {
				String sql = "CREATE TABLE users " + "(id int NOT NULL AUTO_INCREMENT," + " PRIMARY KEY ( id ),"
						+ " first_name VARCHAR(255)," + "" + " last_name VARCHAR(255)," + "email VARCHAR(255),"
						+ "mobile_no VARCHAR(255)," + "" + " password VARCHAR(255)," + "pwd_salt VARCHAR(255),"
						+ " otp VARCHAR(255), " + "" + " otp_expire Long," + " user_name VARCHAR(255),"
						+ "otp_time Date," + "" + "otp_verified_status BOOLEAN," + "status VARCHAR(255),"
						+ "user_number VARCHAR(255) NOT NULL UNIQUE," + "" + "created_at datetime default now(),"
						+ "update_at datetime default now(),"+"role_id int NOT NULL,"+" CONSTRAINT role_id FOREIGN KEY (role_id)  REFERENCES role(role_id))";
				stmt.executeUpdate(sql);
				
			} else {
				System.out.println("Table exists");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		StockType stoc = new StockType();
	}

}
