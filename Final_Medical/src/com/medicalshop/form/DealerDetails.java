package com.medicalshop.form;

import com.medicalshop.config.DatabaseConnection;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;

public class DealerDetails extends JInternalFrame {
  private static final long serialVersionUID = 1L;
  
  private JTable table;
  
  DefaultTableModel model;
  
  DatabaseConnection database = new DatabaseConnection();
  
  static int openFrameCount = 0;
  
  final int xOffset = 30;
  
  final int yOffset = 30;
  
  private JTextField tex_search;
  
  private JLabel inv_type_head;
  
  JComboBox<String> del_name = new JComboBox<>();
  
  String bDate = null;
  
  String delno;
  
  int statuss = 0;
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              DealerDetails frame = new DealerDetails();
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
  
  public DealerDetails() {
    super("Dealer Detail", true, true, true, true);
    setLocation(30 * openFrameCount, 30 * openFrameCount);
    setBounds(100, 100, 1352, 716);
    getContentPane().setLayout((LayoutManager)null);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(57, 114, 1144, 511);
    getContentPane().add(scrollPane);
    this.table = new JTable();
    this.table.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            DefaultTableModel tmodel = (DefaultTableModel)DealerDetails.this.table.getModel();
            int selectedRow = DealerDetails.this.table.getSelectedRow();
            int modelRow = DealerDetails.this.table.convertRowIndexToModel(selectedRow);
            DealerDetails.this.delno = tmodel.getValueAt(modelRow, 1).toString();
          }
        });
    this.table.setDefaultEditor(Object.class, (TableCellEditor)null);
    this.table.setRowHeight(this.table.getRowHeight() + 20);
    this.table.setBackground(Color.WHITE);
    this.model = new DefaultTableModel();
    Object[] Column = { "Sr.No", "Dealer No", "Dealer Name", "Product Name", "Quantity", "Buy Price", "GST_Per", 
        "GST_Amt", "Total", "Buy Date" };
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
            String str = DealerDetails.this.tex_search.getText();
            DealerDetails.this.search(str);
          }
        });
    this.tex_search.setColumns(10);
    this.tex_search.setBounds(1092, 77, 223, 33);
    getContentPane().add(this.tex_search);
    this.inv_type_head = new JLabel("Dealer Detail");
    this.inv_type_head.setForeground(Color.MAGENTA);
    this.inv_type_head.setFont(new Font("Times New Roman", 1, 26));
    this.inv_type_head.setBounds(645, 3, 223, 38);
    getContentPane().add(this.inv_type_head);
    JDateChooser dateChooser = new JDateChooser();
    dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent evt) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            DealerDetails.this.bDate = formatter.format(dateChooser.getDate());
            DealerDetails.this.invTable();
          }
        });
    Date date = new Date();
    dateChooser.setDate(date);
    dateChooser.setBounds(945, 77, 132, 33);
    getContentPane().add((Component)dateChooser);
    this.del_name.addItem("Select Dealer");
    Connection conn2 = this.database.getConnection();
    try {
      PreparedStatement ps3 = conn2.prepareStatement("select del_name from  stockdealer GROUP BY del_name");
      ResultSet rs2 = ps3.executeQuery();
      while (rs2.next())
        this.del_name.addItem(rs2.getString("del_name")); 
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    this.del_name.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            DealerDetails.this.invTable();
          }
        });
    this.del_name.setBounds(695, 77, 236, 33);
    getContentPane().add(this.del_name);
    JButton delete_button_1 = new JButton("Delete");
    delete_button_1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              if (DealerDetails.this.table.getSelectedRow() != -1) {
                Connection con = DealerDetails.this.database.getConnection();
                PreparedStatement ps1 = con.prepareStatement("DELETE FROM stockdealer WHERE del_no='" + DealerDetails.this.delno + "'");
                DealerDetails.this.statuss = ps1.executeUpdate();
                DealerDetails.this.invTable();
                JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
                DealerDetails.this.delno = "";
              } 
            } catch (Exception e1) {
              e1.printStackTrace();
            } 
          }
        });
    delete_button_1.setFont(new Font("Times New Roman", 1, 16));
    delete_button_1.setBounds(1211, 121, 104, 27);
    getContentPane().add(delete_button_1);
  }
  
  public void invTable() {
    this.model.addRow(new Object[0]);
    Connection con = this.database.getConnection();
    try {
      this.model.setRowCount(0);
      String delName = (String)this.del_name.getSelectedItem();
      String sql = "select * from stockdealer WHERE";
      if (this.bDate != null)
        sql = String.valueOf(sql) + " Date(created_at)='" + this.bDate + "' "; 
      if (delName != "Select Dealer" && delName != null)
        sql = String.valueOf(sql) + "AND del_name='" + delName + "' "; 
      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      String type = null;
      int id = 0;
      while (rs.next()) {
        id++;
        String name = rs.getString("del_name");
        Double bStock = Double.valueOf(rs.getDouble("buy_stock"));
        Double bprice = Double.valueOf(rs.getDouble("buy_price"));
        Double total = Double.valueOf(rs.getDouble("total"));
        Double gper = Double.valueOf(rs.getDouble("gst_per"));
        Double gamt = Double.valueOf(rs.getDouble("gst_amt"));
        Date bdate = rs.getDate("created_at");
        int invid = rs.getInt("inv_ids");
        String dno = rs.getString("del_no");
        try {
          PreparedStatement ps1 = con.prepareStatement("select * from inventory where inv_id =" + invid);
          ResultSet rs1 = ps1.executeQuery();
          while (rs1.next())
            type = rs1.getString("inv_name"); 
          this.model.addRow(new Object[] { Integer.valueOf(id), dno, name, type, bStock, bprice, gper, gamt, total, bdate });
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
