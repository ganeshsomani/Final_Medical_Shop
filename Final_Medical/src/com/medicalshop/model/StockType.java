package com.medicalshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.medicalshop.config.DatabaseConnection;

public class StockType {

	public StockType() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			
			Long dataCount = 0L;
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'stock_type'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dataCount = rs.getLong("counts");
			}
			rs.close();
			ps.close();
			if (dataCount == 0) {
				String sql = "CREATE TABLE stock_type " + "(type_id bigint NOT NULL AUTO_INCREMENT," + " PRIMARY KEY ( type_id ),"
						+ "stock_type_name VARCHAR(255)," + "status VARCHAR(255),"
						+ "created_at datetime default now()," + "update_at datetime default now())";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		StockInventory inv = new StockInventory();

	}

}
