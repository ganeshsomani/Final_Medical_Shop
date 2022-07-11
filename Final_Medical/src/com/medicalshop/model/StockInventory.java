package com.medicalshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.medicalshop.config.DatabaseConnection;

public class StockInventory {

	public StockInventory() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			Long dataCount = 0L;
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'inventory'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dataCount = rs.getLong("counts");
			}
			rs.close();
			ps.close();
			if (dataCount == 0) {
				String sql = "CREATE TABLE inventory " + "(inv_id bigint NOT NULL AUTO_INCREMENT," + " PRIMARY KEY ( inv_id ),"
						+ " inv_number VARCHAR(255) NOT NULL UNIQUE," + " inv_name VARCHAR(255),"
						+ "location VARCHAR(255)," + "description VARCHAR(255)," 
						+ "status VARCHAR(255)," + "pack_size LONG ," + ""
						+ "created_at datetime default now()," + "update_at datetime default now(),"
						+ ""+"type_id bigint NOT NULL,"+ " CONSTRAINT type_id FOREIGN KEY (type_id)  REFERENCES stock_type(type_id))";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		Stock stocks = new Stock();
	}

}
