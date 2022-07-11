package com.medicalshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.medicalshop.config.DatabaseConnection;

public class StockDealer {

	public StockDealer() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			Long dataCount = 0L;
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'stockdealer'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dataCount = rs.getLong("counts");
			}
			rs.close();
			ps.close();
			if (dataCount == 0) {
				String sql = "CREATE TABLE stockdealer " + "(del_id bigint NOT NULL AUTO_INCREMENT,"
						+ " PRIMARY KEY ( del_id )," + "buy_stock DOUBLE ," + " buy_price DOUBLE ," + "total DOUBLE  ,"
						+ "gst_per DOUBLE ," + "gst_amt DOUBLE ," + " sell_price DOUBLE ," + "price_tab DOUBLE ,"
						+ "created_at datetime default now()," + "update_at datetime default now()," + ""
						+ "inv_id bigint NOT NULL,"
						+ " CONSTRAINT inv_id FOREIGN KEY (inv_id)  REFERENCES inventory(inv_id))";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}
			@SuppressWarnings("unused")
			Billing bill = new Billing();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
