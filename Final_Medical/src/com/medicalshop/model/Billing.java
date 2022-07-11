package com.medicalshop.model;

import com.medicalshop.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Billing {
	public Billing() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			Long dataCount = Long.valueOf(0L);
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'billing'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				dataCount = Long.valueOf(rs.getLong("counts"));
			rs.close();
			ps.close();
			if (dataCount.longValue() == 0L) {
				String sql = "CREATE TABLE billing (bill_id bigint NOT NULL AUTO_INCREMENT, PRIMARY KEY ( bill_id ),customer_name VARCHAR(255),email VARCHAR(255),mobile_no VARCHAR(255),bill_no VARCHAR(255),discount_amt DOUBLE,discount_per DOUBLE,total DOUBLE,status VARCHAR(255),bill_date datetime,created_at datetime default now(),update_at datetime default now())";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}
			@SuppressWarnings("unused")
			BillMedicine billMedicine = new BillMedicine();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
