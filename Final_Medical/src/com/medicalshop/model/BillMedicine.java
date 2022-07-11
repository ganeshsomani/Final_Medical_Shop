package com.medicalshop.model;

import com.medicalshop.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillMedicine {
	public BillMedicine() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			Long dataCount = Long.valueOf(0L);
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'billMedicine'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				dataCount = Long.valueOf(rs.getLong("counts"));
			rs.close();
			ps.close();
			if (dataCount.longValue() == 0L) {
				String sql = "CREATE TABLE billMedicine (bmed_id bigint NOT NULL AUTO_INCREMENT, PRIMARY KEY ( bmed_id ), medicine_name VARCHAR(255),mprice DOUBLE,quantity DOUBLE , total DOUBLE ,created_at datetime default now(),update_at datetime default now(),bill_id bigint NOT NULL, CONSTRAINT bill_id FOREIGN KEY (bill_id)  REFERENCES billing(bill_id))";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
