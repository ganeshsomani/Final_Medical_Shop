package com.medicalshop.form;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.medicalshop.config.DatabaseConnection;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AddStockTypes extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inv_type;
	private JComboBox<String> sel_status;
	private JLabel status;
	DatabaseConnection database = new DatabaseConnection();
	int statuss = 0;
	int count = 0;
	DefaultTableModel model;
	private JTable table;
	String pnos = new String();
	private JButton Clear;
	JLabel inv_type_err = null;
	static int openFrameCount = 0;
	final int xOffset = 30, yOffset = 30;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStockTypes frame = new AddStockTypes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddStockTypes() {
		super("Add Stock Types", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		// ...Create the GUI and put it in the window...
		// ...Then set the window size or call pack...

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		setBounds(100, 100, 1136, 504);
		getContentPane().setLayout(null);
		setBounds(100, 100, 1169, 377);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel inv_type_head = new JLabel("Inventory Type");
		inv_type_head.setFont(new Font("Times New Roman", Font.BOLD, 26));
		inv_type_head.setForeground(new Color(255, 0, 255));
		inv_type_head.setBounds(455, 45, 177, 38);
		getContentPane().add(inv_type_head);

		JLabel inv_type_nam = new JLabel("InventoryType");
		inv_type_nam.setFont(new Font("Times New Roman", Font.BOLD, 16));
		inv_type_nam.setBounds(63, 141, 104, 22);
		getContentPane().add(inv_type_nam);

		inv_type = new JTextField();
		inv_type.setBounds(211, 135, 305, 28);
		getContentPane().add(inv_type);
		inv_type.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(587, 135, 540, 201);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tmodel = (DefaultTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(selectedRow);
				pnos = tmodel.getValueAt(modelRow, 1).toString();
				inv_type.setText(tmodel.getValueAt(modelRow, 1).toString());
				sel_status.setSelectedItem(tmodel.getValueAt(modelRow, 2).toString());
				// System.out.println(pnos);
			}
		});
		table.setDefaultEditor(Object.class, null);
		table.setRowHeight(table.getRowHeight() + 10);
		table.setBackground(new Color(255, 250, 250));
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		Object[] Column = { "Sr.No", "Stock Type Name", "Status" };
		model.setColumnIdentifiers(Column);
		table.setModel(model);

		sel_status = new JComboBox<String>();
		sel_status.addItem("Active");
		sel_status.addItem("Inactive");
		sel_status.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// selected_text = sel_status.getItemAt(sel_status.getSelectedIndex());
				// System.out.println(selected_text);
			}
		});
		sel_status.setBackground(Color.WHITE);
		sel_status.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		sel_status.setBounds(211, 195, 305, 22);

		getContentPane().add(sel_status);
		status = new JLabel("Status");
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		status.setFont(new Font("Times New Roman", Font.BOLD, 16));
		status.setBounds(79, 195, 88, 22);
		getContentPane().add(status);
		JButton submit_button = new JButton("Submit");
		submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object selected_text = sel_status.getItemAt(sel_status.getSelectedIndex());
				Connection con = database.getConnection();
				PreparedStatement ps;
				PreparedStatement ps1;
				try {
					String invertory_type = inv_type.getText();
					if (pnos.equals("")) {
						ps1 = con.prepareStatement(
								"select COUNT(type_id) AS counts from stock_type WHERE stock_type_name ='"
										+ invertory_type + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							// type.add(rs1.getString("stock_type_name"));
							count = rs1.getInt("counts");
						}

						if (inv_type.getText().equals("")) {
							inv_type_err.setText("Please enter Stock Type");
						} else if (count == 0) {
							ps = con.prepareStatement("insert into stock_type(stock_type_name,status)values ('"
									+ invertory_type + "','" + selected_text + "')");
							statuss = ps.executeUpdate();
							invTable();
							con.close();

						} else {
							JOptionPane.showMessageDialog(null, "Stock type already added");
						}
					} else {
						ps1 = con.prepareStatement(
								"select COUNT(type_id) AS counts from stock_type WHERE stock_type_name ='"
										+ invertory_type + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							count = rs1.getInt("counts");
						}

						if (inv_type.getText().equals("")) {
							inv_type_err.setText("Please enter Stock Type");
						} else if (count == 0) {
							ps = con.prepareStatement("UPDATE stock_type SET stock_type_name='" + invertory_type
									+ "',status = '" + selected_text + "'where stock_type_name = '" + pnos + "'");
							statuss = ps.executeUpdate();
							invTable();
							con.close();

						} else {
							JOptionPane.showMessageDialog(null, "Stock type already added");
						}

					}

					inv_type.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});
		submit_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		submit_button.setBounds(169, 251, 104, 38);
		getContentPane().add(submit_button);

		Clear = new JButton("Clear");
		Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inv_type.setText("");
			}
		});
		Clear.setFont(new Font("Times New Roman", Font.BOLD, 16));
		Clear.setBounds(332, 251, 104, 38);
		getContentPane().add(Clear);

		inv_type_err = new JLabel("");
		inv_type_err.setBounds(211, 164, 305, 14);
		getContentPane().add(inv_type_err);

		JLabel inv_status_err = new JLabel("");
		inv_status_err.setBounds(211, 214, 305, 14);
		getContentPane().add(inv_status_err);
	}

	public void invTable() {
		model.addRow(new Object[] {});
		Connection con = database.getConnection();
		PreparedStatement ps;
		try {
			model.setRowCount(0);
			ps = con.prepareStatement("select * from stock_type");
			ResultSet rs = ps.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = id + 1;
				// int ids = rs.getInt("type_id");
				String st_name = rs.getString("stock_type_name");
				String stat = rs.getString("status");
				// Date cdate = rs.getDate("created_at");
				model.addRow(new Object[] { id, st_name, stat });
			}
			// Object row = new Object(rs);
			// table.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

}
