package com.medicalshop.form;

import com.medicalshop.config.DatabaseConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;

public class AddInventory extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JTextField pname;

	private JTextField psize;

	private JComboBox<String> ptype;

	private JTextPane pdesc;

	private JComboBox<String> pstatus;

	private JTextPane ploc;

	DatabaseConnection database = new DatabaseConnection();

	int statuss = 0;

	private JTable table;

	DefaultTableModel model;

	private JTextField tex_search;

	private String pnos;

	int count = 0;

	private String name;

	String names;

	String inumber;

	String invno;

	static int openFrameCount = 0;

	final int xOffset = 30, yOffset = 30;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddInventory frame = new AddInventory();
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

	public AddInventory() {
		super("Add Inventary", true, true, true, true);
		setLocation(30 * openFrameCount, 30 * openFrameCount);
		setBounds(100, 100, 1352, 716);
		getContentPane().setLayout((LayoutManager) null);
		this.pname = new JTextField();
		this.pname.setBounds(233, 55, 328, 27);
		getContentPane().add(this.pname);
		this.pname.setColumns(10);
		this.ptype = new JComboBox<>();
		Connection conn1 = this.database.getConnection();
		try {
			PreparedStatement ps1 = conn1.prepareStatement("select * from  stock_type WHERE status ='Active' ");
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next())
				this.ptype.addItem(rs1.getString("stock_type_name"));
			this.ptype.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddInventory.this.ptype.getItemAt(AddInventory.this.ptype.getSelectedIndex());
				}
			});
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		this.ptype.setBounds(233, 107, 328, 27);
		getContentPane().add(this.ptype);
		this.psize = new JTextField();
		this.psize.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c))
					e.consume();
			}
		});
		this.psize.setColumns(10);
		this.psize.setBounds(233, 158, 328, 27);
		getContentPane().add(this.psize);
		JLabel invname = new JLabel("Product Name");
		invname.setHorizontalAlignment(4);
		invname.setFont(new Font("Times New Roman", 0, 18));
		invname.setBounds(10, 61, 164, 21);
		getContentPane().add(invname);
		JLabel p_size = new JLabel("Packing Size");
		p_size.setHorizontalAlignment(4);
		p_size.setFont(new Font("Times New Roman", 0, 18));
		p_size.setBounds(10, 159, 164, 21);
		getContentPane().add(p_size);
		JLabel p_type = new JLabel("Product Type");
		p_type.setHorizontalAlignment(4);
		p_type.setFont(new Font("Times New Roman", 0, 18));
		p_type.setBounds(10, 113, 164, 21);
		getContentPane().add(p_type);
		JLabel p_desc = new JLabel("Product Description");
		p_desc.setHorizontalAlignment(4);
		p_desc.setFont(new Font("Times New Roman", 0, 18));
		p_desc.setBounds(10, 224, 164, 21);
		getContentPane().add(p_desc);
		JLabel p_location = new JLabel("Location");
		p_location.setHorizontalAlignment(4);
		p_location.setFont(new Font("Times New Roman", 0, 18));
		p_location.setBounds(10, 302, 164, 21);
		getContentPane().add(p_location);
		JLabel status = new JLabel("Status");
		status.setHorizontalAlignment(4);
		status.setFont(new Font("Times New Roman", 0, 18));
		status.setBounds(10, 372, 164, 21);
		getContentPane().add(status);
		JButton Submit = new JButton("Submit");
		Submit.setFont(new Font("Times New Roman", 1, 16));
		Submit.setBounds(166, 404, 89, 35);
		getContentPane().add(Submit);
		this.pdesc = new JTextPane();
		this.pdesc.setBounds(233, 212, 328, 53);
		getContentPane().add(this.pdesc);
		this.pstatus = new JComboBox<>();
		this.pstatus.addItem("Active");
		this.pstatus.addItem("Inactve");
		this.pstatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.pstatus.setBounds(233, 366, 328, 27);
		getContentPane().add(this.pstatus);
		this.ploc = new JTextPane();
		this.ploc.setBounds(233, 287, 328, 53);
		getContentPane().add(this.ploc);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(589, 55, 715, 613);
		getContentPane().add(scrollPane);
		this.table = new JTable();
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tmodel = (DefaultTableModel) AddInventory.this.table.getModel();
				int selectedRow = AddInventory.this.table.getSelectedRow();
				int modelRow = AddInventory.this.table.convertRowIndexToModel(selectedRow);
				AddInventory.this.pnos = tmodel.getValueAt(modelRow, 1).toString();
				AddInventory.this.pname.setText(tmodel.getValueAt(modelRow, 2).toString());
				AddInventory.this.ptype.setSelectedItem(tmodel.getValueAt(modelRow, 3).toString());
				AddInventory.this.psize.setText(tmodel.getValueAt(modelRow, 6).toString());
				AddInventory.this.pdesc.setText(tmodel.getValueAt(modelRow, 5).toString());
				AddInventory.this.ploc.setText(tmodel.getValueAt(modelRow, 4).toString());
				AddInventory.this.pstatus.setSelectedItem(tmodel.getValueAt(modelRow, 7).toString());
			}
		});
		this.table.setDefaultEditor(Object.class, (TableCellEditor) null);
		this.table.setRowHeight(this.table.getRowHeight() + 10);
		this.table.setBackground(Color.WHITE);
		this.model = new DefaultTableModel();
		Object[] Column = { "SrNo", "P_No.", "P_Name", "P_Type", "Location", "Descprition", "Pack Size", "Status" };
		this.table.getTableHeader().setBackground(Color.GREEN);
		this.table.getTableHeader().setPreferredSize(new Dimension(100, 32));
		Font headerFont = new Font("Times New Roman", 1, 16);
		this.table.getTableHeader().setFont(headerFont);
		this.model.setColumnIdentifiers(Column);
		this.table.setModel(this.model);
		scrollPane.setViewportView(this.table);
		this.tex_search = new JTextField();
		this.tex_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					String str = AddInventory.this.tex_search.getText();
					AddInventory.this.search(str);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		this.tex_search.setBounds(1069, 11, 223, 33);
		getContentPane().add(this.tex_search);
		this.tex_search.setColumns(10);
		final JLabel pErrorMess = new JLabel("");
		pErrorMess.setBounds(233, 82, 328, 14);
		getContentPane().add(pErrorMess);
		JLabel ptypeErrorMess = new JLabel("");
		ptypeErrorMess.setBounds(233, 133, 328, 14);
		getContentPane().add(ptypeErrorMess);
		final JLabel psizeErrorMess = new JLabel("");
		psizeErrorMess.setBounds(233, 184, 328, 14);
		getContentPane().add(psizeErrorMess);
		final JLabel pdescErrorMess = new JLabel("");
		pdescErrorMess.setBounds(233, 264, 328, 14);
		getContentPane().add(pdescErrorMess);
		final JLabel plocErrorMess = new JLabel("");
		plocErrorMess.setBounds(233, 336, 328, 14);
		getContentPane().add(plocErrorMess);
		JLabel pstatusErrorMess = new JLabel("");
		pstatusErrorMess.setBounds(233, 393, 328, 14);
		getContentPane().add(pstatusErrorMess);
		JButton Clear = new JButton("Clear");
		Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddInventory.this.pname.setText("");
				AddInventory.this.ploc.setText("");
				AddInventory.this.pdesc.setText("");
				AddInventory.this.psize.setText("");
				pErrorMess.setText(" ");
				plocErrorMess.setText(" ");
				psizeErrorMess.setText(" ");
				pdescErrorMess.setText(" ");
				AddInventory.this.pnos = null;
			}
		});
		Clear.setFont(new Font("Times New Roman", 1, 16));
		Clear.setBounds(312, 404, 89, 35);
		getContentPane().add(Clear);
		JLabel inv_type_head = new JLabel("Inventory");
		inv_type_head.setForeground(Color.MAGENTA);
		inv_type_head.setFont(new Font("Times New Roman", 1, 26));
		inv_type_head.setBounds(519, 3, 111, 38);
		getContentPane().add(inv_type_head);
		setVisible(true);
		Submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = AddInventory.this.database.getConnection();
				try {
					if (AddInventory.this.pnos == null) {
						PreparedStatement ps1 = con
								.prepareStatement("select COUNT(inv_id) AS counts from inventory WHERE inv_name='"
										+ AddInventory.this.pname.getText() + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next())
							AddInventory.this.count = rs1.getInt("counts");
						if (AddInventory.this.count == 0) {
							if (AddInventory.this.pname.getText().equals("")) {
								pErrorMess.setText("Please enter product name");
							} else if (AddInventory.this.psize.getText().equals("")) {
								psizeErrorMess.setText("Please enter packaging size");
							} else if (AddInventory.this.pdesc.getText().equals("")) {
								pdescErrorMess.setText("Please enter product description");
							} else if (AddInventory.this.ploc.getText().equals("")) {
								plocErrorMess.setText("Please enter product location");
							} else {
								int number = 1;
								PreparedStatement ps4 = con.prepareStatement(
										"select inv_number from inventory ORDER BY inv_number DESC LIMIT 0,1");
								ResultSet rs4 = ps4.executeQuery();
								while (rs4.next())
									AddInventory.this.inumber = rs4.getString("inv_number");
								if (AddInventory.this.inumber != null) {
									System.out.println(AddInventory.this.inumber.substring(3, 13));
									String abc = String.format("%010d", new Object[] {
											Integer.valueOf(Integer.parseInt(inumber.substring(3, 13)) + 1) });
									AddInventory.this.invno = "INV" + abc;
								} else {
									AddInventory.this.invno = "INV" + number;
								}
								String inv_name = AddInventory.this.pname.getText();
								String inv_loc = AddInventory.this.ploc.getText();
								String inv_des = AddInventory.this.pdesc.getText();
								String inv_pac = AddInventory.this.psize.getText();
								Object inv_stus = AddInventory.this.pstatus.getSelectedItem();
								String selected_text = AddInventory.this.ptype
										.getItemAt(AddInventory.this.ptype.getSelectedIndex());
								PreparedStatement ps = con.prepareStatement(
										"insert into inventory(inv_number,inv_name,location,description,pack_size,status,type_id)values ('"
												+ AddInventory.this.invno + "','" + inv_name + "','" + inv_loc + "','"
												+ inv_des + "','" + inv_pac + "','" + inv_stus
												+ "',(select type_id from stock_type where stock_type_name  =  '"
												+ selected_text + "' ))");
								AddInventory.this.statuss = ps.executeUpdate();
								con.close();
								AddInventory.this.invTable();
								AddInventory.this.pname.setText("");
								AddInventory.this.ploc.setText("");
								AddInventory.this.pdesc.setText("");
								AddInventory.this.psize.setText("");
								pErrorMess.setText(" ");
								plocErrorMess.setText(" ");
								psizeErrorMess.setText(" ");
								pdescErrorMess.setText(" ");
								AddInventory.this.pnos = "";
							}
						} else {
							JOptionPane.showMessageDialog(null, "Stock name is already added");
						}
					} else {
						PreparedStatement ps1 = con.prepareStatement(
								"select COUNT(inv_id) AS counts,inv_name,inv_number from inventory WHERE inv_name='"
										+ AddInventory.this.pname.getText() + "'");
						ResultSet rs1 = ps1.executeQuery();
						while (rs1.next()) {
							AddInventory.this.count = rs1.getInt("counts");
							AddInventory.this.name = rs1.getString("inv_number");
						}
						if (AddInventory.this.count == 0) {
							PreparedStatement ps2 = con
									.prepareStatement("select inv_name,inv_number from inventory WHERE inv_number='"
											+ AddInventory.this.pnos + "'");
							ResultSet rs2 = ps2.executeQuery();
							while (rs2.next())
								AddInventory.this.names = rs2.getString("inv_number");
						} else if (AddInventory.this.count != 0) {
							AddInventory.this.names = AddInventory.this.name;
						}
						if (AddInventory.this.names.equalsIgnoreCase(AddInventory.this.pnos)) {
							if (AddInventory.this.pname.getText().equals("")) {
								pErrorMess.setText("Please enter product name");
							} else if (AddInventory.this.psize.getText().equals("")) {
								psizeErrorMess.setText("Please enter packaging size");
							} else if (AddInventory.this.pdesc.getText().equals("")) {
								pdescErrorMess.setText("Please enter product description");
							} else if (AddInventory.this.ploc.getText().equals("")) {
								plocErrorMess.setText("Please enter product location");
							} else {
								String invno = AddInventory.this.pnos;
								String inv_name = AddInventory.this.pname.getText();
								String inv_loc = AddInventory.this.ploc.getText();
								String inv_des = AddInventory.this.pdesc.getText();
								String inv_pac = AddInventory.this.psize.getText();
								Object inv_stus = AddInventory.this.pstatus.getSelectedItem();
								String selected_text = AddInventory.this.ptype
										.getItemAt(AddInventory.this.ptype.getSelectedIndex());
								PreparedStatement ps = con.prepareStatement("UPDATE inventory SET inv_number = '"
										+ invno + "',inv_name ='" + inv_name + "',location ='" + inv_loc
										+ "',description ='" + inv_des + "',status ='" + inv_stus + "',pack_size ='"
										+ inv_pac
										+ "',type_id = (select type_id from stock_type where stock_type_name  =  '"
										+ selected_text + "' ) where inv_number = '" + invno + "'");
								AddInventory.this.statuss = ps.executeUpdate();
								con.close();
								AddInventory.this.invTable();
								AddInventory.this.pname.setText("");
								AddInventory.this.ploc.setText("");
								AddInventory.this.pdesc.setText("");
								AddInventory.this.psize.setText("");
								pErrorMess.setText(" ");
								plocErrorMess.setText(" ");
								psizeErrorMess.setText(" ");
								pdescErrorMess.setText(" ");
								AddInventory.this.pnos = "";
							}
						} else {
							JOptionPane.showMessageDialog(null, "Stock name is already added");
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
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
