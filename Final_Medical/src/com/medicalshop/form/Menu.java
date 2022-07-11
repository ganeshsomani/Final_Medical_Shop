package com.medicalshop.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import com.medicalshop.application.Application;

public class Menu {

	private JFrame frame;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenuItem addStockType = new JMenuItem("Add Stock Type");
	private final JMenuItem addInvData = new JMenuItem("Add Inventory");
	private final JMenuItem billing = new JMenuItem("Billing");
	private JMenuItem addUser;
	JComponent allMyStuff;
	JComponent allMyOtherStuff;
	Users tes = new Users();
	AddStockTypes adds;
	AddInventory addInv;
	AddStock asto;
	Billing bill;
	InventoryDetail invDetail;
	StockDetail stockDet;
	BillingDetail billDetail;
	DealerDetails del;
	private final JMenu Details = new JMenu("Details");
	private final JMenuItem invList = new JMenuItem("Inventory List");
	private final JMenuItem stockList = new JMenuItem("Stock List");
	private final JMenuItem BillingDetails = new JMenuItem("Billing Detail");
	private final JMenuItem delDetail = new JMenuItem("Dealer Detail");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					Application app = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
//				frame.getContentPane().setPreferredSize(new Dimension(frame.getSize()));
//				frame.pack();
//				frame.setVisible(true);
				// tes.setBounds(100, 100, 624, 355);
				if (e.getSource() == frame) {
//					int fheight = frame.getContentPane().getHeight() - menuBar.getHeight();
//					tes.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					adds.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					addInv.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					asto.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					bill.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					invDetail.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					stockDet.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
//					billDetail.setBounds(0, 40, frame.getContentPane().getWidth(), fheight);
				}
			}
		});
		frame.setBounds(100, 100, 1352, 716);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		menuBar.setBackground(new Color(0, 191, 255));
		menuBar.setBounds(0, 0, 1369, 40);
		menuBar.setForeground(Color.WHITE);
		frame.getContentPane().add(menuBar);
		addUser = new JMenuItem("Add User");
		addUser.setPreferredSize(new Dimension(107, 45));
		addUser.setMaximumSize(new Dimension(120, 32767));
		addUser.setBackground(new Color(0, 191, 255));
		addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.getContentPane().add(tes);
					tes.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		addUser.setHorizontalAlignment(SwingConstants.RIGHT);
		addUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(addUser);

		JMenu inventory = new JMenu("Inventory");
		inventory.setBackground(SystemColor.menu);
		inventory.setHorizontalAlignment(SwingConstants.LEFT);
		inventory.setForeground(Color.BLACK);
		inventory.setFont(new Font("Segoe UI", Font.BOLD, 14));
		inventory.setPreferredSize(new Dimension(107, 45));
		inventory.setMaximumSize(new Dimension(120, 32767));
		menuBar.add(inventory);
		addStockType.setBackground(new Color(255, 255, 255));
		addStockType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					adds = new AddStockTypes();
					int fheight1 = frame.getContentPane().getHeight() - menuBar.getHeight();
					adds.setBounds(0, 40, frame.getContentPane().getWidth(), fheight1);
					adds.invTable();
					frame.getContentPane().add(adds);
					adds.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		inventory.add(addStockType);
		addStockType.setFont(new Font("Segoe UI", Font.BOLD, 12));
		addInvData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addInv = new AddInventory();
					int fheight1 = frame.getContentPane().getHeight() - menuBar.getHeight();
					addInv.setBounds(0, 40, frame.getContentPane().getWidth(), fheight1);
					addInv.invTable();
					frame.getContentPane().add(addInv);
					addInv.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		addInvData.setBackground(new Color(255, 255, 255));
		addInvData.setFont(new Font("Segoe UI", Font.BOLD, 12));

		inventory.add(addInvData);

		JMenuItem stock = new JMenuItem("Stock");
		stock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					asto = new AddStock();
					int fheight2 = frame.getContentPane().getHeight() - menuBar.getHeight();
					asto.setBounds(0, 40, frame.getContentPane().getWidth(), fheight2);
					asto.invTable();
					frame.getContentPane().add(asto);
					asto.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		stock.setFont(new Font("Segoe UI", Font.BOLD, 14));
		stock.setPreferredSize(new Dimension(119, 45));
		stock.setMaximumSize(new Dimension(120, 32771));
		stock.setBackground(new Color(0, 191, 255));
		menuBar.add(stock);
		billing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bill = new Billing();
					int fheight2 = frame.getContentPane().getHeight() - menuBar.getHeight();
					bill.setBounds(0, 40, frame.getContentPane().getWidth(), fheight2);
					frame.getContentPane().add(bill);
					bill.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		billing.setPreferredSize(new Dimension(107, 45));
		billing.setMaximumSize(new Dimension(120, 32767));
		billing.setFont(new Font("Segoe UI", Font.BOLD, 14));
		billing.setBackground(new Color(0, 191, 255));

		menuBar.add(billing);
		Details.setPreferredSize(new Dimension(107, 45));
		Details.setMaximumSize(new Dimension(120, 32767));
		Details.setHorizontalAlignment(SwingConstants.LEFT);
		Details.setForeground(Color.BLACK);
		Details.setFont(new Font("Segoe UI", Font.BOLD, 14));
		Details.setBackground(SystemColor.menu);

		menuBar.add(Details);
		invList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					invDetail = new InventoryDetail();
					int fheight6 = frame.getContentPane().getHeight() - Menu.this.menuBar.getHeight();
					invDetail.setBounds(0, 40, Menu.this.frame.getContentPane().getWidth(), fheight6);
					invDetail.invTable();
					frame.getContentPane().add(invDetail);
					invDetail.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		invList.setFont(new Font("Segoe UI", Font.BOLD, 12));
		invList.setBackground(Color.WHITE);

		Details.add(invList);
		stockList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stockDet = new StockDetail();
					int fheight6 = frame.getContentPane().getHeight() - Menu.this.menuBar.getHeight();
					stockDet.setBounds(0, 40, Menu.this.frame.getContentPane().getWidth(), fheight6);
					stockDet.invTable();
					frame.getContentPane().add(stockDet);
					stockDet.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		stockList.setFont(new Font("Segoe UI", Font.BOLD, 12));
		stockList.setBackground(Color.WHITE);

		Details.add(stockList);
		BillingDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					billDetail = new BillingDetail();
					int fheight6 = frame.getContentPane().getHeight() - Menu.this.menuBar.getHeight();
					billDetail.setBounds(0, 40, Menu.this.frame.getContentPane().getWidth(), fheight6);
					billDetail.invTable();
					frame.getContentPane().add(billDetail);
					billDetail.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		BillingDetails.setFont(new Font("Segoe UI", Font.BOLD, 12));
		BillingDetails.setBackground(Color.WHITE);

		Details.add(BillingDetails);
		delDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					del = new DealerDetails();
					int fheight6 = frame.getContentPane().getHeight() - Menu.this.menuBar.getHeight();
					del.setBounds(0, 40, Menu.this.frame.getContentPane().getWidth(), fheight6);
					del.invTable();
					frame.getContentPane().add(del);
					del.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		delDetail.setPreferredSize(new Dimension(107, 45));
		delDetail.setMaximumSize(new Dimension(120, 32767));
		delDetail.setFont(new Font("Segoe UI", Font.BOLD, 14));
		delDetail.setBackground(new Color(0, 191, 255));

		menuBar.add(delDetail);

	}
}
