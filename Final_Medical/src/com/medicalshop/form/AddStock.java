package com.medicalshop.form;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.medicalshop.config.DatabaseConnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class AddStock extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DatabaseConnection database = new DatabaseConnection();
	int statuss = 0;
	private JTextField pQut;
	private JTextField b_prize;
	private JTextField s_price;
	PreparedStatement ps2;
	private String packSize;
	private JTable table;
	DefaultTableModel model;
	String inv_name;
	String pro_no, dnme;
	int type_id;
	String s_type_name;
	private JTextField g_per;
	private JTextField g_amt;
	Double amt;
	String inumber, invno, names, stno, pQuantity;
	int count = 0, invNumber;
	private JTextField search;
	static int openFrameCount = 0;
	final int xOffset = 30, yOffset = 30;
	private JTextField othdelNam = new JTextField();
	JComboBox<String> del_name = new JComboBox<String>();
	String delNo, dNumbers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStock frame = new AddStock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void search(String str) {
		DefaultTableModel tmodel = (DefaultTableModel) table.getModel();
		TableRowSorter<DefaultTableModel> ser = new TableRowSorter<>(tmodel);
		table.setRowSorter(ser);
		ser.setRowFilter(RowFilter.regexFilter(str));
	}

	/**
	 * Create the frame.
	 */
	public AddStock() {
		super("Add Stock", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		// getContentPane().revalidate();
		setBounds(100, 100, 1375, 729);
		getContentPane().setLayout(null);
		JComboBox<String> invName = new JComboBox<String>();
		invName.setBounds(233, 72, 235, 33);
		invName.addItem("Select Medicine");
		getContentPane().add(invName);

		JLabel pname = new JLabel("Product Name");
		pname.setHorizontalAlignment(SwingConstants.RIGHT);
		pname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		pname.setBounds(55, 75, 109, 24);
		getContentPane().add(pname);

		JLabel pquts = new JLabel("Product Quantity");
		pquts.setHorizontalAlignment(SwingConstants.RIGHT);
		pquts.setFont(new Font("Times New Roman", Font.BOLD, 14));
		pquts.setBounds(55, 223, 109, 24);
		getContentPane().add(pquts);

		JLabel bprice = new JLabel("BuyPrice");
		bprice.setHorizontalAlignment(SwingConstants.RIGHT);
		bprice.setFont(new Font("Times New Roman", Font.BOLD, 14));
		bprice.setBounds(55, 271, 109, 24);
		getContentPane().add(bprice);

		JLabel sellprice = new JLabel("Sell Price");
		sellprice.setHorizontalAlignment(SwingConstants.RIGHT);
		sellprice.setFont(new Font("Times New Roman", Font.BOLD, 14));
		sellprice.setBounds(55, 327, 109, 24);
		getContentPane().add(sellprice);

		pQut = new JTextField();
		pQut.setBounds(233, 220, 237, 33);
		getContentPane().add(pQut);
		pQut.setColumns(10);

		b_prize = new JTextField();
		b_prize.setColumns(10);
		b_prize.setBounds(233, 268, 237, 33);
		getContentPane().add(b_prize);

		s_price = new JTextField();
		s_price.setColumns(10);
		s_price.setBounds(233, 324, 237, 33);
		getContentPane().add(s_price);

		JButton submits = new JButton("Submit");
		submits.setFont(new Font("Times New Roman", Font.BOLD, 16));
		submits.setBounds(151, 562, 89, 39);
		getContentPane().add(submits);

		JComboBox<String> statusdd = new JComboBox<String>();
		statusdd.addItem("Active");
		statusdd.addItem("Inactve");
		statusdd.setBounds(233, 482, 237, 33);
		getContentPane().add(statusdd);

		JLabel gst_per = new JLabel("GST Percentage");
		gst_per.setHorizontalAlignment(SwingConstants.RIGHT);
		gst_per.setFont(new Font("Times New Roman", Font.BOLD, 14));
		gst_per.setBounds(55, 381, 109, 24);
		getContentPane().add(gst_per);

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pQut.setText("");
				b_prize.setText("");
				s_price.setText("");
				g_amt.setText("");
				g_per.setText("");
				pro_no = null;
				del_name.setSelectedItem(null);
				invName.setSelectedItem(null);
			}

		});
		clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		clearButton.setBounds(304, 562, 99, 39);
		getContentPane().add(clearButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(540, 74, 779, 517);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tmodel = (DefaultTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				int modelRow = table.convertRowIndexToModel(selectedRow);
				pro_no = tmodel.getValueAt(modelRow, 1).toString();
				invName.setSelectedItem(tmodel.getValueAt(modelRow, 2).toString());
				String abc = tmodel.getValueAt(modelRow, 3).toString();
				String ac = null;
				if (abc.contains(" ")) {
					ac = abc.substring(0, abc.indexOf(" "));
				}
				pQuantity = ac;
				System.out.print(pQuantity);
				pQut.setText(ac);
				b_prize.setText(tmodel.getValueAt(modelRow, 4).toString());
				s_price.setText(tmodel.getValueAt(modelRow, 5).toString());
				g_per.setText(tmodel.getValueAt(modelRow, 7).toString());
				g_amt.setText(tmodel.getValueAt(modelRow, 8).toString());
				del_name.setSelectedIndex(0);
//				System.out.println(pro_no);

			}
		});
		table.setDefaultEditor(Object.class, null);
		table.setRowHeight(table.getRowHeight() + 10);
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		Object[] Column = { "Sr.No", "P.No", "P_Name", "P_Quantity", "Buy Price", "Sale Price", "Price P_tab",
				"GST per.", "GST Amt." };
		table.getTableHeader().setBackground(Color.GREEN);
		table.getTableHeader().setPreferredSize(new Dimension(100, 32));
		Font headerFont = new Font("Times New Roman", Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		model.setColumnIdentifiers(Column);
		table.setModel(model);

		g_per = new JTextField();
		g_per.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (g_per.getText().isEmpty()) {
					{
						g_per.setText("0");
						g_amt.setText("0");
					}
				} else {
					Double tPrice = Double.parseDouble(b_prize.getText()) * Double.parseDouble(pQut.getText());
					// amt = Double.parseDouble(b_prize.getText()) *
					// Double.parseDouble(g_per.getText()) / 100;
					amt = tPrice * Double.parseDouble(g_per.getText()) / 100;
					g_amt.setText(Double.toString(amt));
				}
			}
		});
		g_per.setColumns(10);
		g_per.setBounds(233, 378, 237, 33);
		getContentPane().add(g_per);

		g_amt = new JTextField();
		g_amt.setColumns(10);
		g_amt.setBounds(234, 429, 236, 33);
		g_amt.setEditable(false);
		g_amt.setFont(new Font("Tahoma", Font.BOLD, 11));
		g_amt.setBackground(Color.WHITE);
		getContentPane().add(g_amt);

		JLabel lblGstAmt = new JLabel("GST amt.");
		lblGstAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGstAmt.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblGstAmt.setBounds(55, 432, 109, 24);
		getContentPane().add(lblGstAmt);

		JLabel status = new JLabel("Status");
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		status.setFont(new Font("Times New Roman", Font.BOLD, 14));
		status.setBounds(55, 485, 109, 24);
		getContentPane().add(status);

		JLabel proError = new JLabel("");
		proError.setBounds(235, 106, 233, 14);
		getContentPane().add(proError);

		JLabel proQuaError = new JLabel("");
		proQuaError.setBounds(233, 252, 235, 14);
		getContentPane().add(proQuaError);

		JLabel PbuyError = new JLabel("");
		PbuyError.setBounds(233, 301, 235, 14);
		getContentPane().add(PbuyError);

		JLabel sPError = new JLabel("");
		sPError.setBounds(233, 356, 235, 14);
		getContentPane().add(sPError);

		JLabel GpError = new JLabel("");
		GpError.setBounds(233, 409, 235, 14);
		getContentPane().add(GpError);

		JLabel GaError = new JLabel("");
		GaError.setBounds(235, 464, 233, 14);
		getContentPane().add(GaError);

		JLabel stError = new JLabel("");
		stError.setBounds(233, 515, 235, 14);
		getContentPane().add(stError);

		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String str = search.getText();
				search(str);
			}
		});
		search.setColumns(10);
		search.setBounds(1083, 30, 236, 33);
		getContentPane().add(search);

		JLabel inv_type_head = new JLabel("Stock");
		inv_type_head.setForeground(Color.MAGENTA);
		inv_type_head.setFont(new Font("Times New Roman", Font.BOLD, 26));
		inv_type_head.setBounds(449, 16, 111, 38);
		getContentPane().add(inv_type_head);

		del_name.setBounds(233, 125, 236, 33);
		del_name.addItem("Select Dealer");
		getContentPane().add(del_name);
		Connection conn2 = database.getConnection();
		PreparedStatement ps3;
		try {
			ps3 = conn2.prepareStatement("select del_name from  stockdealer GROUP BY del_name");
			ResultSet rs2 = ps3.executeQuery();
			while (rs2.next()) {
				del_name.addItem(rs2.getString("del_name"));
			}
			del_name.addItem("Other");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		del_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (del_name.getSelectedItem() == "Other") {
					// othdelNam = new JTextField();
					othdelNam.setColumns(10);
					othdelNam.setBounds(233, 176, 237, 33);
					getContentPane().add(othdelNam);
				} else {
					othdelNam.setVisible(false);
					othdelNam = new JTextField();
				}
			}
		});
		JLabel deN = new JLabel("Dealer Name");
		deN.setHorizontalAlignment(SwingConstants.RIGHT);
		deN.setFont(new Font("Times New Roman", Font.BOLD, 14));
		deN.setBounds(55, 128, 109, 24);
		getContentPane().add(deN);

		JLabel delNameError = new JLabel("");
		delNameError.setBounds(233, 157, 235, 14);
		getContentPane().add(delNameError);
		Connection conn1 = database.getConnection();
		PreparedStatement ps1;
		try {
			ps1 = conn1.prepareStatement("select inv_name from  inventory WHERE STATUS = 'Active' ORDER BY inv_name");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				invName.addItem(rs1.getString("inv_name"));
			}
			invName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selected_text = invName.getItemAt(invName.getSelectedIndex());
					try {
						ps2 = conn1.prepareStatement(
								"select pack_size from  inventory WHERE STATUS = 'Active' AND inv_name='"
										+ selected_text + "'");
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							packSize = rs2.getString("pack_size");
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			});
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		submits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = database.getConnection();
				PreparedStatement ps;
				PreparedStatement ps4, ps1, ps2, ps3, ps5, ps6;
				try {

					if (pro_no == null) {
						ps1 = con.prepareStatement("select inv_id from inventory WHERE inv_name='"
								+ invName.getItemAt(invName.getSelectedIndex()) + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							invNumber = rs1.getInt("inv_id");
						}
						ps2 = con.prepareStatement(
								"select COUNT(stock_id) AS counts from stock WHERE inv_id='" + invNumber + "'");
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							count = rs2.getInt("counts");
						}
						if (count == 0) {
							if (invName.getSelectedIndex() == 0) {
								proError.setText("Please select product name");
							} else if (pQut.getText().equals("")) {
								proQuaError.setText("Please enter product quantity");
							} else if (b_prize.getText().equals("")) {
								PbuyError.setText("Please enter product price");
							} else if (s_price.getText().equals("")) {
								sPError.setText("Please enter Sell Price");
							} else if (g_per.getText().equals("")) {
								GpError.setText("Please enter product status");
							} else if (g_amt.getText().equals("")) {
								GaError.setText("Please enter product status");
							} else if (del_name.getSelectedIndex() == 0) {
								delNameError.setText("Please enter Dealer Name");
							} else {
								int number = 0000001;
								int dnumber = 0000000001;
								ps4 = con.prepareStatement(
										"select stock_no from stock ORDER BY stock_no DESC LIMIT 0,1");
								ResultSet rs4 = ps4.executeQuery();
								while (rs4.next()) {
									inumber = rs4.getString("stock_no");
								}
								if (inumber != null) {
									String abc = String.format("%07d", Integer.parseInt(inumber.substring(2, 9)) + 1);
									invno = "SM" + abc;
								} else {
									invno = "SM" + number;
								}
								if (del_name.getSelectedIndex() != 0) {
									if (del_name.getSelectedItem().equals("Other")) {
										dnme = othdelNam.getText();
									} else {
										dnme = (String) del_name.getSelectedItem();
									}
								} else {

								}
								ps6 = con.prepareStatement("select del_no from stock ORDER BY del_no DESC LIMIT 0,1");
								ResultSet rs6 = ps6.executeQuery();
								while (rs6.next()) {
									dNumbers = rs6.getString("del_no");
								}
								if (dNumbers != null) {
									String abc = String.format("%010d",
											Integer.parseInt(dNumbers.substring(1, 11)) + 1);
									delNo = "D" + abc;
								} else {
									delNo = "D" + dnumber;
								}
								String stcno = invno;
								String selected_text = invName.getItemAt(invName.getSelectedIndex());
								String selected_status = statusdd.getItemAt(statusdd.getSelectedIndex());
								String pqut = pQut.getText();
								String bprices = b_prize.getText();
								String sprices = s_price.getText();
								Double ppertab = Double.parseDouble(s_price.getText()) / Double.parseDouble(packSize);
								Double atab = Double.parseDouble(pQut.getText()) * Double.parseDouble(packSize);
								String pergst = g_per.getText();
								String amtgst = g_amt.getText();
								Double tot = Double.parseDouble(pQut.getText()) * Double.parseDouble(bprices);
								ps = con.prepareStatement(
										"insert into stock(stock_no,avail_stock,avail_tab,buy_price,sell_price,price_tab,status,gst_per,gst_amt,inv_id)values ('"
												+ stcno + "','" + pqut + "','" + atab + "','" + bprices + "','"
												+ sprices + "','" + ppertab + "','" + selected_status + "' , '" + pergst
												+ "','" + amtgst
												+ "',(select inv_id from inventory where inv_name  =  '" + selected_text
												+ "' ))");
								statuss = ps.executeUpdate();
								ps3 = con.prepareStatement(
										"insert into stockdealer(buy_stock,buy_price,total,gst_per,gst_amt,del_name,del_no,inv_ids)values ('"
												+ pqut + "','" + bprices + "','" + tot + "','" + pergst + "','" + amtgst
												+ "','" + dnme + "','" + delNo
												+ "',(select inv_id from inventory where inv_name  =  '" + selected_text
												+ "' ))");
								statuss = ps3.executeUpdate();
								invTable();
								con.close();
								pQut.setText("");
								b_prize.setText("");
								s_price.setText("");
								g_amt.setText("");
								g_per.setText("");
								pro_no = null;
								othdelNam.setText("");
								del_name.setSelectedItem(null);
								invName.setSelectedItem(null);
							}
						} else {

							JOptionPane.showMessageDialog(null, "Stock  is already added");
							count = 0;
						}
					} else {
						int dnumber = 0000000001;
						ps1 = con.prepareStatement("select inv_id,inv_name from inventory WHERE inv_name='"
								+ invName.getItemAt(invName.getSelectedIndex()) + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							invNumber = rs1.getInt("inv_id");
							names = rs1.getString("inv_name");
						}
						ps2 = con.prepareStatement("select COUNT(stock_id) AS counts,stock_no from stock WHERE inv_id='"
								+ invNumber + "'");
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							count = rs2.getInt("counts");
							stno = rs2.getString("stock_no");
						}
						// if (count == 0 ||
						// names.equalsIgnoreCase(invName.getItemAt(invName.getSelectedIndex()))) {
						if (del_name.getSelectedItem().equals("Other")) {
							dnme = othdelNam.getText();
						} else {
							dnme = (String) del_name.getSelectedItem();
						}
						ps6 = con.prepareStatement("select del_no from stock ORDER BY del_no DESC LIMIT 0,1");
						ResultSet rs6 = ps6.executeQuery();
						while (rs6.next()) {
							dNumbers = rs6.getString("del_no");
						}
						if (dNumbers != null) {
							String abc = String.format("%010d", Integer.parseInt(dNumbers.substring(1, 11)) + 1);
							delNo = "D" + abc;
						} else {
							delNo = "D" + dnumber;
						}

						if (count == 0 || stno.equalsIgnoreCase(pro_no)) {
							if (invName.getSelectedIndex() == 0) {
								proError.setText("Please select product name");
							} else if (pQut.getText().equals("")) {
								proQuaError.setText("Please enter product quantity");
							} else if (b_prize.getText().equals("")) {
								PbuyError.setText("Please enter product price");
							} else if (s_price.getText().equals("")) {
								sPError.setText("Please enter Sell Price");
							} else if (g_per.getText().equals("")) {
								GpError.setText("Please enter product status");
							} else if (g_amt.getText().equals("")) {
								GaError.setText("Please enter product status");
							} else if (del_name.getSelectedIndex() == 0) {
								delNameError.setText("Please enter Dealer Name");
							} else {
								// String stcno = "SM" + Utils.generateRandomNumber(5);
								String selected_text = invName.getItemAt(invName.getSelectedIndex());
								String selected_status = statusdd.getItemAt(statusdd.getSelectedIndex());

								Double pqutFinal = Double.parseDouble(pQuantity) + Double.parseDouble(pQut.getText());
								String pqut = Double.toString(pqutFinal);
								String bprices = b_prize.getText();
								String sprices = s_price.getText();
								Double ppertab = Double.parseDouble(s_price.getText()) / Double.parseDouble(packSize);
								Double atab = pqutFinal * Double.parseDouble(packSize);
								String pergst = g_per.getText();
								String amtgst = g_amt.getText();
								Double tot1 = Double.parseDouble(pQut.getText()) * Double.parseDouble(bprices);
								ps = con.prepareStatement("UPDATE stock SET stock_no='" + pro_no + "',avail_stock='"
										+ pqut + "',avail_tab='" + atab + "',buy_price='" + bprices + "'"
										+ ",sell_price='" + sprices + "',price_tab='" + ppertab + "',status='"
										+ selected_status + "',gst_per='" + pergst + "',gst_amt='" + amtgst
										+ "',inv_id = (select inv_id from inventory where inv_name  = '" + selected_text
										+ "') where stock_no ='" + pro_no + "' ");
								statuss = ps.executeUpdate();

								ps5 = con.prepareStatement(
										"insert into stockdealer(buy_stock,buy_price,total,gst_per,gst_amt,del_name,inv_ids)values ('"
												+ pQut.getText() + "','" + bprices + "','" + tot1 + "','" + pergst
												+ "','" + amtgst + "','" + dnme
												+ "',(select inv_id from inventory where inv_name  =  '" + selected_text
												+ "' ))");
								statuss = ps5.executeUpdate();
								invTable();
								con.close();
								pQut.setText("");
								b_prize.setText("");
								s_price.setText("");
								g_amt.setText("");
								g_per.setText("");
								pro_no = "";
								del_name.setSelectedItem(null);
								invName.setSelectedItem(null);
								othdelNam.setText("");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Stock  is already added");
							count = 0;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public void invTable() {
		model.addRow(new Object[] {});
		Connection con = database.getConnection();
		PreparedStatement ps;
		try {
			model.setRowCount(0);
			ps = con.prepareStatement("select * from stock");
			ResultSet rs = ps.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = id + 1;
				String ids = rs.getString("stock_no");
				Double avail_stock = rs.getDouble("avail_stock");
				Double avail_tab = rs.getDouble("avail_tab");
				Double b_price = rs.getDouble("buy_price");
				Double s_price = rs.getDouble("sell_price");
				Double p_tab = rs.getDouble("price_tab");
				Double g_price = rs.getDouble("gst_amt");
				Double g_percentage = rs.getDouble("gst_per");
				String stat = rs.getString("status");
				int inv_id = rs.getInt("inv_id");
				PreparedStatement ps1;
				try {
					ps1 = con.prepareStatement("select inv_name,type_id from inventory where inv_id =" + inv_id);
					ResultSet rs1 = ps1.executeQuery();
					while (rs1.next()) {
						inv_name = rs1.getString("inv_name");
						type_id = rs1.getInt("type_id");
						PreparedStatement ps2;
						try {
							ps2 = con.prepareStatement(
									"select stock_type_name from stock_type where type_id =" + type_id);
							ResultSet rs2 = ps2.executeQuery();
							while (rs2.next()) {
								s_type_name = rs2.getString("stock_type_name");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.addRow(new Object[] { id, ids, inv_name, avail_stock + " " + s_type_name, b_price, s_price, p_tab,
						g_percentage, g_price });
			}
			// Object row = new Object(rs);
			// table.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
