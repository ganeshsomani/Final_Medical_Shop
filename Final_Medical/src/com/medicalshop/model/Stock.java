package com.medicalshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.medicalshop.config.DatabaseConnection;

public class Stock {

	public Stock() {
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = db.getConnection();
		try {
			Long dataCount = 0L;
			PreparedStatement ps = null;
			Statement stmt = conn.createStatement();
			String sql1 = "SELECT COUNT(*) as counts FROM information_schema.tables  WHERE table_schema = 'medical_shop'  AND table_name = 'stock'";
			ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dataCount = rs.getLong("counts");
			}
			rs.close();
			ps.close();
			if (dataCount == 0) {
				String sql = "CREATE TABLE stock " + "(stock_id bigint NOT NULL AUTO_INCREMENT,"
						+ " PRIMARY KEY ( stock_id )," + " stock_no VARCHAR(255) NOT NULL UNIQUE,"
						+ "status VARCHAR(255)," + "avail_stock DOUBLE ," + " avail_tab DOUBLE ,"
						+ "buy_price DOUBLE  ," + "gst_per DOUBLE ," + "gst_amt DOUBLE ,"
						+ " sell_price DOUBLE ," + "price_tab DOUBLE ," + "buy_price_tab DOUBLE ," + "created_at datetime default now(),"
						+ "update_at datetime default now()," + "" + "inv_id bigint NOT NULL,"
						+ " CONSTRAINT inv_id FOREIGN KEY (inv_id)  REFERENCES inventory(inv_id))";
				stmt.executeUpdate(sql);
			} else {
				System.out.println("Table exists");
			}
			@SuppressWarnings("unused")
			StockDealer stdealer = new StockDealer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
