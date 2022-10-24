/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class TrangChuPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	public TrangChuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		//
		JPanel pnlMain = new javax.swing.JPanel();
		JPanel pnlC = new javax.swing.JPanel();
		JLabel lbIcon = new javax.swing.JLabel();
		JPanel pnlE = new javax.swing.JPanel();
		JLabel lbPhealis = new javax.swing.JLabel();
		JLabel lbDescription = new javax.swing.JLabel();
		JPanel pnlD = new javax.swing.JPanel();
		JLabel lbDot1 = new javax.swing.JLabel();
		JLabel lbTC1 = new javax.swing.JLabel();
		JLabel lbDot2 = new javax.swing.JLabel();
		JLabel lbTC2 = new javax.swing.JLabel();
		JLabel lbDot3 = new javax.swing.JLabel();
		JLabel lbTC3 = new javax.swing.JLabel();
		JPanel pnlA = new javax.swing.JPanel();
		JLabel lbImg1 = new javax.swing.JLabel();
		JLabel lbImg2 = new javax.swing.JLabel();
		JPanel pnlB = new javax.swing.JPanel();
		btnQLHD = new ColoredButton();
		btnTK = new ColoredButton();

		setBackground(new java.awt.Color(255, 255, 255));
		setLayout(new java.awt.BorderLayout());

		pnlMain.setOpaque(false);
		pnlMain.setLayout(new javax.swing.BoxLayout(pnlMain, javax.swing.BoxLayout.Y_AXIS));

		pnlC.setOpaque(false);
		pnlC.setLayout(new javax.swing.BoxLayout(pnlC, javax.swing.BoxLayout.LINE_AXIS));

		lbIcon.setIcon(new javax.swing.ImageIcon("Images/icon.png")); // NOI18N
		pnlC.add(lbIcon);

		pnlE.setOpaque(false);
		pnlE.setLayout(new javax.swing.BoxLayout(pnlE, javax.swing.BoxLayout.Y_AXIS));

		lbPhealis.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
		lbPhealis.setForeground(new java.awt.Color(0, 204, 51));
		lbPhealis.setText("Phealis");
		pnlE.add(lbPhealis);

		lbDescription.setText("Hệ thống quản lý nhà thuốc");
		pnlE.add(lbDescription);

		pnlC.add(pnlE);
		pnlC.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		pnlMain.add(pnlC);
		pnlMain.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20)));

		pnlD.setOpaque(false);
		pnlD.setLayout(new javax.swing.BoxLayout(pnlD, javax.swing.BoxLayout.LINE_AXIS));
		pnlD.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		lbDot1.setIcon(new javax.swing.ImageIcon("Images/dot.png")); // NOI18N
		pnlD.add(lbDot1);

		lbTC1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		lbTC1.setText("Nhanh chóng");
		pnlD.add(lbTC1);
		pnlD.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		lbDot2.setIcon(new javax.swing.ImageIcon("Images/dot.png")); // NOI18N
		pnlD.add(lbDot2);

		lbTC2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		lbTC2.setText("Dễ dàng");
		pnlD.add(lbTC2);
		pnlD.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		lbDot3.setIcon(new javax.swing.ImageIcon("Images/dot.png")); // NOI18N
		pnlD.add(lbDot3);

		lbTC3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		lbTC3.setText("Thuận tiện");
		pnlD.add(lbTC3);
		pnlD.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		pnlMain.add(pnlD);
		pnlMain.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20)));

		add(pnlMain, java.awt.BorderLayout.NORTH);

		pnlA.setOpaque(false);
		pnlA.setLayout(new javax.swing.BoxLayout(pnlA, javax.swing.BoxLayout.LINE_AXIS));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767)));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));

		lbImg1.setIcon(new javax.swing.ImageIcon("Images/nhathuoc.jpg")); // NOI18N
		pnlA.add(lbImg1);
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));

		lbImg2.setIcon(new javax.swing.ImageIcon("Images/nhathuoc2.jpg")); // NOI18N
		pnlA.add(lbImg2);
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)));
		pnlA.add(new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767)));

		add(pnlA, java.awt.BorderLayout.CENTER);

		pnlB.setOpaque(false);
		pnlB.add(new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));

		btnQLHD.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
		btnQLHD.setIcon(new javax.swing.ImageIcon("Images/order.png")); // NOI18N
		btnQLHD.setText("Quản lý hóa đơn");
		btnQLHD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnQLHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnQLHD.setBackground(new Color(0xE7E7E7));
		btnQLHD.setBorderRadius(20);
		btnQLHD.setForeground(Color.black);
		pnlB.add(btnQLHD);
		pnlB.add(new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));


		if(MainFrame.getNhanVien().getLoaiNhanVien() == 0) {
			btnQLNV = new ColoredButton();

			btnQLNV.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
			btnQLNV.setIcon(new javax.swing.ImageIcon("Images/employee.png")); // NOI18N
			btnQLNV.setText("Quản lý nhân viên");
			btnQLNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			btnQLNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			btnQLNV.setBackground(new Color(0xE7E7E7));
			btnQLNV.setForeground(Color.black);
			btnQLNV.setBorderRadius(20);
			pnlB.add(btnQLNV);
			pnlB.add(new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));
		}

		btnTK.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
		btnTK.setIcon(new javax.swing.ImageIcon("Images/statistics_64px.png")); // NOI18N
		btnTK.setText("Thống kê");
		btnTK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnTK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnTK.setBackground(new Color(0xE7E7E7));
		btnTK.setForeground(Color.black);
		btnTK.setBorderRadius(20);
		pnlB.add(btnTK);
		pnlB.add(new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));

		add(pnlB, java.awt.BorderLayout.SOUTH);

		addEvent();
	}

	private void addEvent() {

		btnQLHD.addActionListener((e) -> {
			mainFrame.changeCenter(mainFrame.getQuanLyHoaDonPanel());
		});

		if(btnQLNV != null) {
			btnQLNV.addActionListener((e) -> {
				mainFrame.changeCenter(mainFrame.getQuanLyNhanVienPanel());
			});
		}

	}

	private ColoredButton btnQLNV;
	private ColoredButton btnTK;
	private ColoredButton btnQLHD;
}
