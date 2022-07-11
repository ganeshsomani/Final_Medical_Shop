package com.medicalshop.form;

import com.medicalshop.config.DatabaseConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;

public class StockDetail extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JTable table;

	DefaultTableModel model;

	DatabaseConnection database = new DatabaseConnection();

	static int openFrameCount = 0;

	final int xOffset = 30;

	final int yOffset = 30;

	String inv_name;

	String s_type_name;

	int type_id;

	private JTextField tex_search;

	private JLabel inv_type_head;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockDetail frame = new StockDetail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void search(String str) {
		DefaultTableModel tmodel = (DefaultTableModel) this.table.getModel();
		TableRowSorter<DefaultTableModel> ser = new TableRowSorter<>(tmodel);
		this.table.setRowSorter((RowSorter) ser);
		ser.setRowFilter(RowFilter.regexFilter(str, new int[0]));
	}

	public StockDetail() {
		super("stock Detail", true, true, true, true);
		setLocation(30 * openFrameCount, 30 * openFrameCount);
		setBounds(100, 100, 1375, 729);
		getContentPane().setLayout((LayoutManager) null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 66, 1268, 560);
		getContentPane().add(scrollPane);
		this.table = new JTable();
		this.table.setDefaultEditor(Object.class, (TableCellEditor) null);
		this.table.setRowHeight(this.table.getRowHeight() + 20);
		this.table.setBackground(Color.WHITE);
		this.model = new DefaultTableModel();
		Object[] Column = { "Sr.No", "Product No", "Product Name", "Product Quantity", "Total Quantity", "Buy Price",
				"Sale Price", "Price P_tab", "GST per.", "GST Amt." };
		this.table.getTableHeader().setBackground(Color.GREEN);
		this.table.getTableHeader().setPreferredSize(new Dimension(100, 32));
		Font headerFont = new Font("Times New Roman", 1, 16);
		this.table.getTableHeader().setFont(headerFont);
		this.model.setColumnIdentifiers(Column);
		this.table.setModel(this.model);
		scrollPane.setViewportView(this.table);
		this.tex_search = new JTextField();
		this.tex_search.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String str = StockDetail.this.tex_search.getText();
				StockDetail.this.search(str);
			}
		});
		this.tex_search.setColumns(10);
		this.tex_search.setBounds(1092, 11, 223, 33);
		getContentPane().add(this.tex_search);
		this.inv_type_head = new JLabel("Stock Details");
		this.inv_type_head.setForeground(Color.MAGENTA);
		this.inv_type_head.setFont(new Font("Times New Roman", 1, 26));
		this.inv_type_head.setBounds(423, 17, 170, 38);
		getContentPane().add(this.inv_type_head);
	}

	public void invTable() {
		this.model.addRow(new Object[0]);
		Connection con = this.database.getConnection();
		try {
			this.model.setRowCount(0);
			PreparedStatement ps = con.prepareStatement("select * from stock");
			ResultSet rs = ps.executeQuery();
			int id = 0;
			while (rs.next()) {
				id++;
				String ids = rs.getString("stock_no");
				Double avail_stock = Double.valueOf(rs.getDouble("avail_stock"));
				Double avail_tab = Double.valueOf(rs.getDouble("avail_tab"));
				Double b_price = Double.valueOf(rs.getDouble("buy_price"));
				Double s_price = Double.valueOf(rs.getDouble("sell_price"));
				Double p_tab = Double.valueOf(rs.getDouble("price_tab"));
				Double g_price = Double.valueOf(rs.getDouble("gst_amt"));
				Double g_percentage = Double.valueOf(rs.getDouble("gst_per"));
				int inv_id = rs.getInt("inv_id");
				try {
					PreparedStatement ps1 = con
							.prepareStatement("select inv_name,type_id from inventory where inv_id =" + inv_id);
					ResultSet rs1 = ps1.executeQuery();
					while (rs1.next()) {
						this.inv_name = rs1.getString("inv_name");
						this.type_id = rs1.getInt("type_id");
						try {
							PreparedStatement ps2 = con.prepareStatement(
									"select stock_type_name from stock_type where type_id =" + this.type_id);
							ResultSet rs2 = ps2.executeQuery();
							while (rs2.next())
								this.s_type_name = rs2.getString("stock_type_name");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.model.addRow(
						new Object[] { Integer.valueOf(id), ids, this.inv_name, avail_stock + " " + this.s_type_name,
								avail_tab, b_price, s_price, p_tab, g_percentage, g_price });
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
