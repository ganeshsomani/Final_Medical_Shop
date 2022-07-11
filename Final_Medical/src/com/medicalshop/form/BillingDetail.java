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
import java.util.Date;
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

public class BillingDetail extends JInternalFrame {
  private static final long serialVersionUID = 1L;
  
  static int openFrameCount = 0;
  
  final int xOffset = 30;
  
  final int yOffset = 30;
  
  private JTextField tex_search;
  
  private JTable table;
  
  DefaultTableModel model;
  
  DatabaseConnection database = new DatabaseConnection();
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              BillingDetail frame = new BillingDetail();
              frame.setVisible(true);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          }
        });
  }
  
  public void search(String str) {
    try {
      DefaultTableModel tmodel = (DefaultTableModel)this.table.getModel();
      TableRowSorter<DefaultTableModel> ser = new TableRowSorter<>(tmodel);
      this.table.setRowSorter((RowSorter)ser);
      ser.setRowFilter(RowFilter.regexFilter(str, new int[0]));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public BillingDetail() {
    super("Bills Detail", true, true, true, true);
    setLocation(30 * openFrameCount, 30 * openFrameCount);
    setBounds(100, 100, 1352, 716);
    getContentPane().setLayout((LayoutManager)null);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(42, 73, 1258, 548);
    getContentPane().add(scrollPane);
    this.table = new JTable();
    this.table.setDefaultEditor(Object.class, (TableCellEditor)null);
    this.table.setRowHeight(this.table.getRowHeight() + 20);
    this.table.setBackground(Color.WHITE);
    this.model = new DefaultTableModel();
    Object[] Column = { "Sr.No", "Bill_no", "Cus. Name", "Email", "Mobile No.", "Dis.Amt", "Dis.Per", "Total", 
        "Bill Date", "Status" };
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
            String str = BillingDetail.this.tex_search.getText();
            BillingDetail.this.search(str);
          }
        });
    this.tex_search.setColumns(10);
    this.tex_search.setBounds(1077, 29, 223, 33);
    getContentPane().add(this.tex_search);
    JLabel inv_type_head = new JLabel("Bills");
    inv_type_head.setForeground(Color.MAGENTA);
    inv_type_head.setFont(new Font("Times New Roman", 1, 26));
    inv_type_head.setBounds(614, 24, 223, 38);
    getContentPane().add(inv_type_head);
  }
  
  public void invTable() {
    this.model.addRow(new Object[0]);
    Connection con = this.database.getConnection();
    try {
      this.model.setRowCount(0);
      PreparedStatement ps = con.prepareStatement("SELECT * FROM billing");
      ResultSet rs = ps.executeQuery();
      int id = 0;
      while (rs.next()) {
        id++;
        String cusname = rs.getString("customer_name");
        String email = rs.getString("email");
        String moNo = rs.getString("mobile_no");
        String bno = rs.getString("bill_no");
        Double disAmt = Double.valueOf(rs.getDouble("discount_amt"));
        Double disPer = Double.valueOf(rs.getDouble("discount_per"));
        Double tot = Double.valueOf(rs.getDouble("total"));
        String stat = rs.getString("status");
        Date dat = rs.getDate("bill_date");
        this.model.addRow(new Object[] { Integer.valueOf(id), bno, cusname, email, moNo, disAmt, disPer, tot, dat, stat });
      } 
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
