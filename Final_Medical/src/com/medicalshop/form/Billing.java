package com.medicalshop.form;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.medicalshop.config.DatabaseConnection;
import com.toedter.calendar.JDateChooser;
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
import javax.swing.Box;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class Billing extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField cName;
	private JTextField cMob;
	private JTextField cEmail;
	private JTextField quantity;
	private JTextField price;
	private JTextField Total;
	private JTable table;
	private JTextField aTotal;
	private JTextField dPer;
	private JTextField dAmt;
	private JTextField finalTotal;
	DatabaseConnection database = new DatabaseConnection();
	PreparedStatement ps2;
	private String invName;
	int invid;
	String selected_text = "", pnos, invno, times;
	Double disPr = 0.0;
	Double availTabs, atabs = 0.0, aStocks = 0.0;
	Double buyPrices;
	Double fTotal = 0.0;
	Double priceTabs = 0.0;
	Double finalpriceTabs = 0.0;
	Double prices = 0.0;
	DefaultTableModel model;
	DefaultTableModel tmodel;
	int id = 0, bilId;
	Double sums = 0.0;
	int statuss = 0, count = 0, invNumber;
	String inumber;
	Map<Integer, String> invn = new HashMap<>();
	static int openFrameCount = 0;
	final int xOffset = 30, yOffset = 30;
	String dia = "dialogue";
	private JTextField buyprice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Billing frame = new Billing();
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
	public Billing() {
		super("Billing", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		setBounds(100, 100, 1370, 805);
		getContentPane().setLayout(null);
		cName = new JTextField();
		cName.setColumns(10);
		cName.setBounds(105, 46, 328, 27);
		getContentPane().add(cName);

		JLabel cusName = new JLabel("Name");
		cusName.setHorizontalAlignment(SwingConstants.RIGHT);
		cusName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		cusName.setBounds(-79, 47, 164, 21);
		getContentPane().add(cusName);

		JLabel cusMobile = new JLabel("Mobile No.");
		cusMobile.setHorizontalAlignment(SwingConstants.RIGHT);
		cusMobile.setFont(new Font("Times New Roman", Font.BOLD, 18));
		cusMobile.setBounds(365, 47, 164, 21);
		getContentPane().add(cusMobile);

		cMob = new JTextField();
		cMob.setColumns(10);
		cMob.setBounds(539, 46, 164, 27);
		getContentPane().add(cMob);

		cEmail = new JTextField();
		cEmail.setColumns(10);
		cEmail.setBounds(802, 46, 227, 27);
		getContentPane().add(cEmail);

		JLabel cusEmail = new JLabel("Email");
		cusEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		cusEmail.setFont(new Font("Times New Roman", Font.BOLD, 18));
		cusEmail.setBounds(611, 52, 164, 21);
		getContentPane().add(cusEmail);

		JDateChooser dateChooser = new JDateChooser();
		// formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		dateChooser.setDate(date);
		dateChooser.setBounds(1027, 12, 132, 27);
		getContentPane().add(dateChooser);

		JComboBox<String> tabName = new JComboBox<String>();
		tabName.addItem("Select Medicine");
		Connection conn1 = database.getConnection();
		PreparedStatement ps1;
		try {
			ps1 = conn1.prepareStatement("select * from  inventory WHERE STATUS = 'Active' ORDER BY inv_name ASC");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()) {
				tabName.addItem(rs1.getString("inv_name"));
				invName = rs1.getString("inv_name");
				invid = rs1.getInt("inv_id");
				invn.put(invid, invName);
			}
			tabName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						selected_text = tabName.getItemAt(tabName.getSelectedIndex());
						quantity.setText("0");
						for (Map.Entry<Integer, String> in : invn.entrySet()) {
							if (in.getValue().equals(selected_text)) {
								// System.out.println("kEY==" + in.getKey());
								invid = in.getKey();
							}
						}
						ps2 = conn1.prepareStatement(
								"select price_tab,avail_stock,avail_tab,buy_price_tab from  stock WHERE STATUS = 'Active' AND inv_id='"
										+ invid + "'");
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							priceTabs = rs2.getDouble("price_tab");
							aStocks = rs2.getDouble("avail_stock");
							atabs = rs2.getDouble("avail_tab");
							buyPrices = rs2.getDouble("buy_price_tab");
						}
						if (atabs != 0 && aStocks != 0) {
							price.setEditable(true);
							quantity.setEditable(true);
							if (priceTabs != 0) {
								price.setText(Double.toString(priceTabs));
								buyprice.setText(Double.toString(buyPrices));
								finalpriceTabs = priceTabs;
								priceTabs = 0.0;
								aStocks = 0.0;
								atabs = 0.0;
								buyPrices = 0.0;
							} else {
								price.setText("0");
							}
						} else {
							if (dia == "dialogue") {
								JOptionPane.showMessageDialog(null, "Stock is not available");
							}
							price.setText("0");
							aStocks = 0.0;
							atabs = 0.0;
							price.setEditable(false);
							quantity.setEditable(false);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tabName.setBounds(20, 160, 207, 27);
		getContentPane().add(tabName);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setForeground(Color.BLACK);
		horizontalBox.setBackground(Color.BLACK);
		horizontalBox.setBorder(UIManager.getBorder("Tree.editorBorder"));
		horizontalBox.setBounds(10, 147, 1142, 2);
		getContentPane().add(horizontalBox);

		JLabel mName = new JLabel("Medicine");
		mName.setHorizontalAlignment(SwingConstants.RIGHT);
		mName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		mName.setBounds(-13, 115, 164, 21);
		getContentPane().add(mName);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuantity.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblQuantity.setBounds(365, 115, 164, 21);
		getContentPane().add(lblQuantity);

		quantity = new JTextField();
		quantity.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (quantity.getText().isEmpty()) {
						Total.setText("0");
					} else {
						prices = Double.parseDouble(price.getText()) * Double.parseDouble(quantity.getText());
						Total.setText(String.valueOf(prices));
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		quantity.setColumns(10);
		quantity.setBounds(422, 160, 164, 27);
		getContentPane().add(quantity);

		price = new JTextField();
		price.setColumns(10);
		price.setBounds(237, 160, 164, 27);

		getContentPane().add(price);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblPrice.setBounds(161, 115, 164, 21);
		getContentPane().add(lblPrice);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblTotal.setBounds(550, 115, 164, 21);
		getContentPane().add(lblTotal);

		Total = new JTextField();
		Total.setEditable(false);
		Total.setFont(new Font("Tahoma", Font.BOLD, 11));
		Total.setBackground(Color.WHITE);
		Total.setColumns(10);
		Total.setBounds(611, 160, 209, 27);
		getContentPane().add(Total);

		JLabel lblAction = new JLabel("Action");
		lblAction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAction.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAction.setBounds(763, 115, 164, 21);
		getContentPane().add(lblAction);

		JLabel nameError = new JLabel("");
		nameError.setBounds(105, 72, 328, 14);
		getContentPane().add(nameError);

		JLabel moError = new JLabel("");
		moError.setBounds(539, 72, 164, 14);
		getContentPane().add(moError);

		JLabel emailError = new JLabel("");
		emailError.setBounds(802, 72, 230, 14);
		getContentPane().add(emailError);

		JLabel priError = new JLabel("");
		priError.setBounds(237, 188, 164, 14);
		getContentPane().add(priError);

		JLabel quaError = new JLabel("");
		quaError.setBounds(422, 188, 164, 14);
		getContentPane().add(quaError);

		JLabel totError = new JLabel("");
		totError.setBounds(611, 188, 207, 14);
		getContentPane().add(totError);

		JLabel medError = new JLabel("");
		medError.setBounds(20, 188, 207, 14);
		getContentPane().add(medError);

		JLabel atotError = new JLabel("");
		atotError.setBounds(950, 454, 230, 14);
		getContentPane().add(atotError);

		JLabel disPError = new JLabel("");
		disPError.setBounds(950, 506, 230, 14);
		getContentPane().add(disPError);

		JLabel disAmtError = new JLabel("");
		disAmtError.setBounds(950, 560, 230, 14);
		getContentPane().add(disAmtError);

		JLabel billTotError = new JLabel("");
		billTotError.setBounds(950, 661, 230, 14);
		getContentPane().add(billTotError);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 213, 1149, 162);
		getContentPane().add(scrollPane);

		buyprice = new JTextField();
		buyprice.setEnabled(false);
		buyprice.setVisible(false);
		buyprice.setFont(new Font("Tahoma", Font.BOLD, 11));
		buyprice.setEditable(false);
		buyprice.setColumns(10);
		buyprice.setBackground(Color.WHITE);
		buyprice.setBounds(862, 188, 95, 21);
		getContentPane().add(buyprice);

		table = new JTable();
//		try {
//			table.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					tmodel = (DefaultTableModel) table.getModel();
//					int selectedRow = table.getSelectedRow();
//					int modelRow = table.convertRowIndexToModel(selectedRow);
//					pnos = tmodel.getValueAt(modelRow, 0).toString();
//					tabName.setSelectedItem(tmodel.getValueAt(modelRow, 1).toString());
//					price.setText(tmodel.getValueAt(modelRow, 2).toString());
//					quantity.setText(tmodel.getValueAt(modelRow, 3).toString());
//					Total.setText(tmodel.getValueAt(modelRow, 4).toString());
//					// System.out.println("Table Data==" + tmodel.getRowCount());
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		table.setDefaultEditor(Object.class, null);
		table.setRowHeight(table.getRowHeight() + 10);
		table.setBackground(Color.WHITE);
		model = new DefaultTableModel();
		Object[] Column = { "Sr.No", "Medicine", "Price", "Quantity", "Total", "BPrice" };
		table.getTableHeader().setBackground(Color.GREEN);
		table.getTableHeader().setPreferredSize(new Dimension(100, 32));
		Font headerFont = new Font("Times New Roman", Font.BOLD, 16);
		table.getTableHeader().setFont(headerFont);
		model.setColumnIdentifiers(Column);
		table.setModel(model);
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getColumnModel().getColumn(5).setWidth(0);
		scrollPane.setViewportView(table);

		JButton add_button = new JButton("Add");

		add_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (tabName.getSelectedIndex() == 0) {
						medError.setText("Please select product name");
					} else if (price.getText().equals("")) {
						priError.setText("Please enter price");
					} else if (quantity.getText().equals("")) {
						quaError.setText("Please enter product quality");
					} else if (Total.getText().equals("")) {
						totError.setText("Please enter product total");
					} else {
						id = id + 1;
						String med = (String) tabName.getSelectedItem();
						String prc = price.getText();
						String qut = quantity.getText();
						String tot = Total.getText();
						String bpri = buyprice.getText();
						model.addRow(new Object[] { id, med, prc, qut, tot, bpri });
						sums = 0.0;
						price.setText("");
						quantity.setText("");
						Total.setText("");
					}

				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});

		add_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add_button.setBounds(862, 159, 104, 27);
		getContentPane().add(add_button);

		JLabel lblAmountTotal = new JLabel("Amount Total");
		lblAmountTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmountTotal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAmountTotal.setBounds(763, 377, 164, 21);
		getContentPane().add(lblAmountTotal);

		JLabel lblDiscountPercentage = new JLabel("Discount Percentage");
		lblDiscountPercentage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiscountPercentage.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblDiscountPercentage.setBounds(763, 428, 164, 21);
		getContentPane().add(lblDiscountPercentage);

		JLabel lblDiscountAmount = new JLabel("Discount Amount");
		lblDiscountAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiscountAmount.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblDiscountAmount.setBounds(763, 480, 164, 21);
		getContentPane().add(lblDiscountAmount);

		JLabel lblBillingTotal = new JLabel("Billing Total ");
		lblBillingTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBillingTotal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblBillingTotal.setBounds(763, 549, 164, 21);
		getContentPane().add(lblBillingTotal);

		aTotal = new JTextField();
		aTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		aTotal.setBackground(Color.WHITE);
		aTotal.setColumns(10);
		aTotal.setBounds(950, 376, 209, 27);
		aTotal.setEditable(false);
		getContentPane().add(aTotal);

		dAmt = new JTextField();
		dAmt.setColumns(10);
		dAmt.setBounds(950, 479, 209, 27);
		getContentPane().add(dAmt);

		finalTotal = new JTextField();
		finalTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		finalTotal.setBackground(Color.WHITE);
		finalTotal.setColumns(10);
		finalTotal.setBounds(950, 531, 209, 61);
		finalTotal.setEditable(false);
		;
		getContentPane().add(finalTotal);

		JButton clear_button = new JButton("Clear");
		clear_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				price.setText("");
				quantity.setText("");
				Total.setText("");
			}
		});
		clear_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clear_button.setBounds(997, 160, 104, 27);
		getContentPane().add(clear_button);

		JButton delete_button_1 = new JButton("Delete");
		delete_button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					model.removeRow(table.getSelectedRow());
					JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
					sums = 0.0;
				}
			}
		});
		delete_button_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		delete_button_1.setBounds(1169, 212, 104, 27);
		getContentPane().add(delete_button_1);

		JButton total_button = new JButton("Total");
		total_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					tmodel = (DefaultTableModel) table.getModel();
					ArrayList<String> p = new ArrayList<>();
					for (int i = 0; i < tmodel.getRowCount(); i++) {
						p.add((String) table.getValueAt(i, 4));
					}
					for (String a : p) {
						Double sum = Double.parseDouble(a);
						sums = Double.sum(sums, sum);
					}
					aTotal.setText(Double.toString(sums));
					dAmt.setText("");
					dPer.setText("");
					finalTotal.setText(Double.toString(sums));
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
		});
		total_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		total_button.setBounds(1169, 375, 104, 27);
		getContentPane().add(total_button);

		dPer = new JTextField();
		dPer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (dPer.getText().isEmpty()) {
						dAmt.setText("");
						dPer.setText("");
					} else {
						disPr = Double.parseDouble(aTotal.getText()) * Double.parseDouble(dPer.getText()) / 100;
						dAmt.setText(Double.toString(disPr));
						fTotal = sums - disPr;
						finalTotal.setText(Double.toString(fTotal));
					}
				} catch (Exception e5) {
					e5.printStackTrace();
				}
			}
		});
		dPer.setColumns(10);
		dPer.setBounds(950, 431, 209, 27);
		getContentPane().add(dPer);

		JButton save_button = new JButton("Save");
		save_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dia = null;
				Connection con = database.getConnection();
				PreparedStatement ps, ps1, ps2, ps3, ps4, ps5;
				int inid;
				Long pacSize;
				String stocNo;
				Double aStock, aTab, rtab, rstock;
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					String t = dtf.format(now);
					times = formatter.format(dateChooser.getDate()) + " " + t;
					// System.out.println("Mouse Updated new==" + times);
					String number = "0000000001";
					ps = con.prepareStatement("select bill_no from billing ORDER BY bill_no DESC LIMIT 0,1");
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						inumber = rs.getString("bill_no");
					}
					if (inumber != null) {
						String abc = String.format("%010d", Integer.parseInt(inumber.substring(1, 11)) + 1);
						invno = "S" + abc;
					} else {
						invno = "S" + number;
					}
					if (cName.getText().equals("")) {
						nameError.setText("Please enter customer name");
					} else if (cEmail.getText().equals("")) {
						emailError.setText("Please enter customer email");
					} else if (cMob.getText().equals("")) {
						moError.setText("Please enter customer mobile no");
					} else if (aTotal.getText().equals("")) {
						atotError.setText("Please enter product total");
					} else if (dPer.getText().equals("")) {
						disPError.setText("Please enter product discount");
					} else if (dAmt.getText().equals("")) {
						disAmtError.setText("Please enter product discount amount");
					} else if (finalTotal.getText().equals("")) {
						billTotError.setText("Please enter  total");
					} else {
						ps1 = con.prepareStatement(
								"insert into billing(customer_name,email,mobile_no,bill_no,discount_amt,discount_per,total,status,bill_date)values ('"
										+ cName.getText() + "','" + cEmail.getText() + "','" + cMob.getText() + "','"
										+ invno + "','" + dAmt.getText() + "','" + dPer.getText() + "','"
										+ finalTotal.getText() + "','" + "Paid" + "','" + times + "')");
						statuss = ps1.executeUpdate();
						for (int i = 0; model.getRowCount() > i; i++) {
							tmodel = (DefaultTableModel) table.getModel();
							// pnos = tmodel.getValueAt(i, 0).toString();
							String n = tmodel.getValueAt(i, 1).toString();
							String p = tmodel.getValueAt(i, 2).toString();
							String q = tmodel.getValueAt(i, 3).toString();
							String ts = tmodel.getValueAt(i, 4).toString();
							String bpr = tmodel.getValueAt(i, 5).toString();
							System.out.println(bpr);
							ps2 = con.prepareStatement(
									"insert into billmedicine(medicine_name,mprice,quantity,total,buyprice,bill_id)values ('"
											+ n + "','" + p + "','" + q + "','" + ts + "','" + bpr
											+ "',(select bill_id from billing where bill_no  = '" + invno + "' ))");
							statuss = ps2.executeUpdate();

							ps3 = con.prepareStatement(
									"SELECT inv_id,pack_size FROM inventory WHERE inv_name= '" + n + "'");
							ResultSet rs3 = ps3.executeQuery();
							while (rs3.next()) {
								inid = rs3.getInt("inv_id");
								pacSize = rs3.getLong("pack_size");

								ps4 = con.prepareStatement(
										"SELECT stock_no,avail_stock,avail_tab FROM stock WHERE inv_id=  '" + inid
												+ "'");
								ResultSet rs4 = ps4.executeQuery();
								while (rs4.next()) {
									stocNo = rs4.getString("stock_no");
									aStock = rs4.getDouble("avail_stock");
									aTab = rs4.getDouble("avail_tab");
									rtab = aTab - Double.parseDouble(q);
									rstock = rtab / pacSize;

									ps5 = con.prepareStatement("UPDATE stock SET stock_no='" + stocNo
											+ "',avail_stock='" + rstock + "',avail_tab='" + rtab + "',inv_id = '"
											+ inid + "' where stock_no ='" + stocNo + "' ");
									statuss = ps5.executeUpdate();
//									System.out.println("Avail Stock=" + aStock);
//									System.out.println("Avail tab=" + aStock);
//									System.out.println("Re Stock =" + rstock);
//									System.out.println("Re Tab=" + rtab);

								}
							}

						}
						con.close();
						cName.setText("");
						cMob.setText("");
						cEmail.setText("");
						tabName.setSelectedIndex(0);
						price.setText("");
						quantity.setText("");
						Total.setText("");
						aTotal.setText("");
						dPer.setText("");
						dAmt.setText("");
						finalTotal.setText("");
						tmodel.setRowCount(0);
						dia = "dialogue";
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		save_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		save_button.setBounds(1169, 426, 104, 27);
		getContentPane().add(save_button);

		JButton print_button = new JButton("Print");
		print_button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		print_button.setBounds(1169, 479, 104, 27);
		getContentPane().add(print_button);

		JLabel inv_type_head = new JLabel("Billing");
		inv_type_head.setForeground(Color.MAGENTA);
		inv_type_head.setFont(new Font("Times New Roman", Font.BOLD, 26));
		inv_type_head.setBounds(464, 0, 111, 38);
		getContentPane().add(inv_type_head);

	}
}
