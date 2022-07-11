package com.medicalshop.form;

import com.medicalshop.config.DatabaseConnection;
import com.medicalshop.utility.HashGenerator;
import com.medicalshop.utility.Utils;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Users extends JInternalFrame {
  private JTextField firstName;
  
  private JTextField lastName;
  
  private JTextField email;
  
  private JTextField mobileNo;
  
  private JLabel lblLastName;
  
  private JLabel lblEmail;
  
  private JLabel lblMobileNo;
  
  private JLabel lblPassword;
  
  private JLabel lblConfirmPassword;
  
  private JPasswordField Cpassword;
  
  private JPasswordField password;
  
  DatabaseConnection database = new DatabaseConnection();
  
  int status = 0;
  
  private static final long serialVersionUID = 1L;
  
  static int openFrameCount = 0;
  
  final int xOffset = 30, yOffset = 30;
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              Users fram = new Users();
              fram.setVisible(true);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          }
        });
  }
  
  public Users() {
    super("Users" + ++openFrameCount, true, true, true, true);
    setLocation(30 * openFrameCount, 30 * openFrameCount);
    setBounds(100, 100, 716, 549);
    getContentPane().setLayout((LayoutManager)null);
    getContentPane().setFont(new Font("Times New Roman", 0, 12));
    setBounds(100, 100, 712, 489);
    getContentPane().setLayout((LayoutManager)null);
    this.firstName = new JTextField();
    this.firstName.setFont(new Font("Times New Roman", 0, 12));
    this.firstName.setBounds(217, 52, 456, 20);
    getContentPane().add(this.firstName);
    this.firstName.setColumns(10);
    this.lastName = new JTextField();
    this.lastName.setFont(new Font("Times New Roman", 0, 12));
    this.lastName.setBounds(217, 95, 456, 20);
    getContentPane().add(this.lastName);
    this.lastName.setColumns(10);
    this.email = new JTextField();
    this.email.setFont(new Font("Times New Roman", 0, 12));
    this.email.setBounds(217, 135, 456, 20);
    getContentPane().add(this.email);
    this.email.setColumns(10);
    this.mobileNo = new JTextField();
    this.mobileNo.setFont(new Font("Times New Roman", 0, 12));
    this.mobileNo.setBounds(217, 177, 456, 20);
    getContentPane().add(this.mobileNo);
    this.mobileNo.setColumns(10);
    JLabel lblNewLabel = new JLabel("First Name");
    lblNewLabel.setHorizontalAlignment(4);
    lblNewLabel.setFont(new Font("Times New Roman", 0, 16));
    lblNewLabel.setBounds(58, 53, 92, 17);
    getContentPane().add(lblNewLabel);
    JLabel lblAddUser = new JLabel("Add User");
    lblAddUser.setHorizontalAlignment(0);
    lblAddUser.setFont(new Font("Times New Roman", 1, 22));
    lblAddUser.setBounds(351, 11, 134, 25);
    getContentPane().add(lblAddUser);
    this.lblLastName = new JLabel("Last Name");
    this.lblLastName.setHorizontalAlignment(4);
    this.lblLastName.setFont(new Font("Times New Roman", 0, 16));
    this.lblLastName.setBounds(58, 96, 92, 17);
    getContentPane().add(this.lblLastName);
    this.lblEmail = new JLabel("Email");
    this.lblEmail.setHorizontalAlignment(4);
    this.lblEmail.setFont(new Font("Times New Roman", 0, 16));
    this.lblEmail.setBounds(58, 136, 92, 17);
    getContentPane().add(this.lblEmail);
    this.lblMobileNo = new JLabel("Mobile No.");
    this.lblMobileNo.setHorizontalAlignment(4);
    this.lblMobileNo.setFont(new Font("Times New Roman", 0, 16));
    this.lblMobileNo.setBounds(58, 191, 92, 17);
    getContentPane().add(this.lblMobileNo);
    this.lblPassword = new JLabel("Password");
    this.lblPassword.setHorizontalAlignment(4);
    this.lblPassword.setFont(new Font("Times New Roman", 0, 16));
    this.lblPassword.setBounds(58, 232, 92, 17);
    getContentPane().add(this.lblPassword);
    this.lblConfirmPassword = new JLabel("Confirm Password");
    this.lblConfirmPassword.setHorizontalAlignment(4);
    this.lblConfirmPassword.setFont(new Font("Times New Roman", 0, 16));
    this.lblConfirmPassword.setBounds(27, 279, 123, 17);
    getContentPane().add(this.lblConfirmPassword);
    final JComboBox<String> Role = new JComboBox<>();
    Role.setBackground(Color.WHITE);
    Connection conn1 = this.database.getConnection();
    try {
      PreparedStatement ps1 = conn1.prepareStatement("select * from  role");
      ResultSet rs1 = ps1.executeQuery();
      while (rs1.next())
        Role.addItem(rs1.getString("role")); 
      Role.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String selected_text = Role.getItemAt(Role.getSelectedIndex());
              System.out.println(selected_text);
            }
          });
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    JButton btnNewButton = new JButton("Submit");
    btnNewButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Connection con = Users.this.database.getConnection();
            String finaPassword = null;
            try {
              String firstNames = Users.this.firstName.getText();
              String lastNames = Users.this.lastName.getText();
              String emails = Users.this.email.getText();
              String mobileNos = Users.this.mobileNo.getText();
              String passwords = Users.this.password.getText();
              String conPass = Users.this.Cpassword.getText();
              String Username = "USER" + Utils.generateRandomNumber(10);
              String salt12 = Utils.getAlphaNumString(5, 5);
              String selected_text1 = Role.getItemAt(Role.getSelectedIndex());
              try {
                finaPassword = HashGenerator.generateHash(passwords, salt12);
              } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
              } 
              String statuse = "Active";
              System.out.println(selected_text1);
              PreparedStatement ps = con.prepareStatement(
                  "insert into users(first_name,last_name,email,mobile_no,password,pwd_salt,user_number,status,role_id) values('" + 
                  firstNames + "','" + lastNames + "','" + emails + "', '" + mobileNos + 
                  "','" + finaPassword + "','" + salt12 + "','" + Username + "','" + statuse + 
                  "', (select role_id from role where role  =  '" + selected_text1 + "' ))");
              Users.this.status = ps.executeUpdate();
              con.close();
            } catch (SQLException e1) {
              e1.printStackTrace();
            } 
          }
        });
    btnNewButton.setFont(new Font("Times New Roman", 1, 14));
    btnNewButton.setBounds(396, 374, 89, 36);
    getContentPane().add(btnNewButton);
    this.Cpassword = new JPasswordField();
    this.Cpassword.setBounds(217, 278, 456, 20);
    getContentPane().add(this.Cpassword);
    this.password = new JPasswordField();
    this.password.setBounds(217, 231, 456, 20);
    getContentPane().add(this.password);
    JLabel role = new JLabel("Role");
    role.setHorizontalAlignment(4);
    role.setFont(new Font("Times New Roman", 0, 16));
    role.setBounds(84, 325, 66, 25);
    getContentPane().add(role);
    Role.setFont(new Font("Times New Roman", 0, 16));
    Role.setBounds(217, 327, 456, 22);
    getContentPane().add(Role);
  }
}
