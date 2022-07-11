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

public class InventoryDetail extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JTable table;

	DefaultTableModel model;

	DatabaseConnection database = new DatabaseConnection();

	static int openFrameCount = 0;

	final int xOffset = 30;

	final int yOffset = 30;

	private JTextField tex_search;

	private JLabel inv_type_head;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryDetail frame = new InventoryDetail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void search(String str) {
		try {
			DefaultTableModel tmodel = (DefaultTableModel) this.table.getModel();
			TableRowSorter<DefaultTableModel> ser = new TableRowSorter<>(tmodel);
			this.table.setRowSorter((RowSorter) ser);
			ser.setRowFilter(RowFilter.regexFilter(str, new int[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InventoryDetail() {
		super("Inventary Detail", true, true, true, true);
		setLocation(30 * openFrameCount, 30 * openFrameCount);
		setBounds(100, 100, 1352, 716);
		getContentPane().setLayout((LayoutManager) null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 51, 1258, 574);
		getContentPane().add(scrollPane);
		this.table = new JTable();
		this.table.setDefaultEditor(Object.class, (TableCellEditor) null);
		this.table.setRowHeight(this.table.getRowHeight() + 20);
		this.table.setBackground(Color.WHITE);
		this.model = new DefaultTableModel();
		Object[] Column = { "Sr.No", "Product No.", "Product Name", "Product Type", "Location", "Descprition",
				"Pack Size", "Status" };
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
				String str = InventoryDetail.this.tex_search.getText();
				InventoryDetail.this.search(str);
			}
		});
		this.tex_search.setColumns(10);
		this.tex_search.setBounds(1092, 11, 223, 33);
		getContentPane().add(this.tex_search);
		this.inv_type_head = new JLabel("Inventory Detail");
		this.inv_type_head.setForeground(Color.MAGENTA);
		this.inv_type_head.setFont(new Font("Times New Roman", 1, 26));
		this.inv_type_head.setBounds(645, 3, 223, 38);
		getContentPane().add(this.inv_type_head);
	}

	public void invTable() {
		this.model.addRow(new Object[0]);
		Connection con = this.database.getConnection();
		try {
			this.model.setRowCount(0);
			PreparedStatement ps = con.prepareStatement("select * from inventory");
			ResultSet rs = ps.executeQuery();
			String type = null;
			int id = 0;
			while (rs.next()) {
				id++;
				String s_number = rs.getString("inv_number");
				String name = rs.getString("inv_name");
				String loc = rs.getString("location");
				String desc = rs.getString("description");
				String psize = rs.getString("pack_size");
				String stat = rs.getString("status");
				try {
					PreparedStatement ps1 = con
							.prepareStatement("select * from stock_type where type_id =" + rs.getString("type_id"));
					ResultSet rs1 = ps1.executeQuery();
					while (rs1.next())
						type = rs1.getString("stock_type_name");
					this.model
							.addRow(new Object[] { Integer.valueOf(id), s_number, name, type, loc, desc, psize, stat });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
