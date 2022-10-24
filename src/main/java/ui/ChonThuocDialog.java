package main.java.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import main.java.dao.ThuocDAO;
import main.java.entity.PhanLoai;
import main.java.entity.Thuoc;


public class ChonThuocDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColoredButton btnTimKiem;
	private CustomTable tableThuoc;
	private DefaultTableModel modelThuoc;
	private ColoredButton btnChon;
	private ColoredButton btnQuayLai;

	private CapNhatHoaDonDialog owner;
	private JTextField txtTen;
	private JComboBox<String> cbbDVT;
	private JTextField txtQCDG;
	private JComboBox<String> cbbNuocSX;
	private JComboBox<String> cbbDangBaoChe;

	private List<Thuoc> thuocs;
	private ThuocDAO thuocDAO;
	private DefaultComboBoxModel<String> phanLoaiModel;
	private JComboBox<String> cbbPhanLoai;
	private JLabel lbPage;
	private ColoredButton btnHome;
	private ColoredButton btnEnd;
	private ColoredButton btnBefore;
	private ColoredButton btnNext;
	private int currentIndex = 0;

	public ChonThuocDialog(CapNhatHoaDonDialog owner) {
		super(owner);
		this.owner = owner;
		setSize(owner.getSize());
		setTitle("Chọn thuốc");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("Images/customer.png").getImage());
		setLayout(new BorderLayout());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				owner.setEnabled(true);

			}
			@Override
			public void windowActivated(WindowEvent e) {
				owner.setEnabled(false);
			}
		});

		getContentPane().setBackground(Color.white);

		this.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
		this.add(Box.createHorizontalStrut(5), BorderLayout.WEST);

		addNorth();
		addCenter();
		addSouth();

		addEvent();

		thuocDAO = new ThuocDAO();
	}

	private void timThuoc() {
		modelThuoc.setRowCount(0);
		modelThuoc.fireTableDataChanged();

		PhanLoai phanLoai = null;
		if(cbbPhanLoai.getSelectedIndex() == 1)
			phanLoai = PhanLoai.THUOC_BAN_THEO_DON;
		if(cbbPhanLoai.getSelectedIndex() == 2)
			phanLoai = PhanLoai.THUOC_KHONG_THEO_DON;

		thuocs = thuocDAO.timThuoc(txtTen.getText().trim(), txtQCDG.getText().trim(), cbbDVT.getSelectedItem().toString(), cbbNuocSX.getSelectedItem().toString().trim(), cbbDangBaoChe.getSelectedItem().toString(), phanLoai, 1);

		if(thuocs.size() == 0) {
			return;
		}

		taiDuLieuLenBang(thuocs, 0);
	}

	private void addSouth() {
		btnChon = new ColoredButton("Chọn", new ImageIcon("Images/add.png"));
		btnChon.setBackground(UIConstant.DANGER_COLOR);

		btnQuayLai = new ColoredButton("Quay lại", new ImageIcon("Images/back.png"));
		btnQuayLai.setBackground(UIConstant.WARNING_COLOR);

		Box boxButtonCTHD;
		this.add(boxButtonCTHD = Box.createHorizontalBox(), BorderLayout.SOUTH);

		boxButtonCTHD.add(Box.createVerticalStrut(50));
		boxButtonCTHD.add(Box.createHorizontalGlue());
		boxButtonCTHD.add(btnChon);
		boxButtonCTHD.add(Box.createHorizontalGlue());
		boxButtonCTHD.add(btnQuayLai);
		boxButtonCTHD.add(Box.createHorizontalGlue());
		boxButtonCTHD.add(Box.createVerticalStrut(50));
	}

	private void addCenter() {
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setOpaque(false);
		this.add(pnlCenter, BorderLayout.CENTER);

		tableThuoc = new CustomTable();
		tableThuoc.setModel(modelThuoc = new DefaultTableModel(new String[] {"Hình ảnh", "Mã thuốc", 
				"Tên thuốc", "Nhà cung cấp", "Đóng gói", "Dạng bào chế", "Đơn vị tính", 
				"Đơn giá", "Phân loại", "Nước sản xuất", "Trạng thái", "Số lượng tồn"}, 0));
		tableThuoc.getColumn("Hình ảnh").setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if(column == 0) {
					if(value instanceof String)
						return new ImageItem(new ImageIcon(value.toString()));
					else
						return new ImageItem((byte[])value);
				}

				return (Component) value;

			}
		});
		tableThuoc.setRowHeight(60);
		tableThuoc.getColumn("Hình ảnh").setMaxWidth(80);
		tableThuoc.getColumn("Hình ảnh").setMinWidth(80);
		tableThuoc.setFont(UIConstant.NORMAL_FONT);


		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setOpaque(false);

		pnlCenter.add(tabPane, BorderLayout.CENTER);

		JScrollPane scroll;
		tabPane.addTab("Kết quả tìm kiếm", scroll = new JScrollPane(tableThuoc));
		scroll.getViewport().setBackground(Color.white);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		lbPage = new JLabel("Trang 1 trong 1 trang."); lbPage.setFont(UIConstant.NORMAL_FONT);

		btnHome = new ColoredButton(new ImageIcon("Images/double_left.png")); btnHome.setBackground(UIConstant.LINE_COLOR);
		btnEnd = new ColoredButton(new ImageIcon("Images/double_right.png")); btnEnd.setBackground(UIConstant.LINE_COLOR);
		btnBefore = new ColoredButton(new ImageIcon("Images/left.png")); btnBefore.setBackground(UIConstant.LINE_COLOR);
		btnNext = new ColoredButton(new ImageIcon("Images/right.png")); btnNext.setBackground(UIConstant.LINE_COLOR);

		btnHome.setToolTipText("Trang đầu");
		btnEnd.setToolTipText("Trang cuối");
		btnBefore.setToolTipText("Trang trước");
		btnNext.setToolTipText("Trang kế tiếp");

		Box boxPage = Box.createHorizontalBox();
		boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnHome); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnBefore); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnNext); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(btnEnd); boxPage.add(Box.createHorizontalStrut(5));
		boxPage.add(Box.createHorizontalGlue());
		boxPage.add(lbPage); boxPage.add(Box.createHorizontalStrut(5));

		pnlCenter.add(boxPage, BorderLayout.SOUTH);
	}

	private void addNorth() {
		Box boxM = Box.createHorizontalBox();
		Box boxNorth = Box.createVerticalBox();
		boxM.add(Box.createHorizontalStrut(5));
		boxM.add(boxNorth);
		boxM.add(Box.createHorizontalStrut(5));
		this.add(boxM, BorderLayout.NORTH);

		JLabel lbTenThuoc = new JLabel("Tên thuốc");
		JLabel lbDVT = new JLabel("Đơn vị tính");
		JLabel lbQCDG = new JLabel("Quy cách đóng gói");
		JLabel lbPhanLoai = new JLabel("Phân loại");
		JLabel lbNuocSX = new JLabel("Nước sản xuất");
		JLabel lbDangBaoChe = new JLabel("Dạng bào chế");

		lbTenThuoc.setFont(UIConstant.NORMAL_FONT);		lbTenThuoc.setPreferredSize(new Dimension(120,20));
		lbDVT.setFont(UIConstant.NORMAL_FONT);		lbDVT.setPreferredSize(new Dimension(90,20));
		lbQCDG.setFont(UIConstant.NORMAL_FONT);		lbQCDG.setPreferredSize(new Dimension(120,20));
		lbPhanLoai.setFont(UIConstant.NORMAL_FONT);		lbPhanLoai.setPreferredSize(new Dimension(90,20));
		lbNuocSX.setFont(UIConstant.NORMAL_FONT);		lbNuocSX.setPreferredSize(new Dimension(120,20));
		lbDangBaoChe.setFont(UIConstant.NORMAL_FONT);		lbDangBaoChe.setPreferredSize(new Dimension(120,20));

		txtTen = new JTextField(30); txtTen.setFont(UIConstant.NORMAL_FONT);
		cbbDVT = new JComboBox<String>(QuanLyThuocPanel.getDonViTinhModel()); cbbDVT.setFont(UIConstant.NORMAL_FONT);
		cbbDVT.setSelectedIndex(0);
		txtQCDG = new JTextField(); txtQCDG.setFont(UIConstant.NORMAL_FONT);
		cbbNuocSX = new JComboBox<String>(QuanLyThuocPanel.getNuocSXModel());; cbbNuocSX.setFont(UIConstant.NORMAL_FONT);
		cbbNuocSX.setSelectedIndex(0);
		cbbDangBaoChe = new JComboBox<String>(QuanLyThuocPanel.getDangBaoCheModel());; cbbDangBaoChe.setFont(UIConstant.NORMAL_FONT);
		cbbDangBaoChe.setSelectedIndex(0);
		phanLoaiModel = new DefaultComboBoxModel<String>();
		phanLoaiModel.addElement("Tất cả");
		phanLoaiModel.addElement("Có kê đơn");
		phanLoaiModel.addElement("Không kê đơn");
		cbbPhanLoai = new JComboBox<String>(phanLoaiModel); cbbPhanLoai.setFont(UIConstant.NORMAL_FONT);
		cbbPhanLoai.setSelectedIndex(0);

		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();

		boxNorth.add(box1);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box2);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box3);
		boxNorth.add(Box.createVerticalStrut(5));
		boxNorth.add(box4);
		boxNorth.add(Box.createVerticalStrut(5));

		box1.add(lbTenThuoc); box1.add(Box.createHorizontalStrut(5));
		box1.add(txtTen); box1.add(Box.createHorizontalStrut(5));
		box1.add(lbDVT); box1.add(Box.createHorizontalStrut(5));
		box1.add(cbbDVT); box1.add(Box.createHorizontalStrut(5));

		box2.add(lbQCDG); box2.add(Box.createHorizontalStrut(5));
		box2.add(txtQCDG); box2.add(Box.createHorizontalStrut(5));
		box2.add(lbNuocSX); box2.add(Box.createHorizontalStrut(5));
		box2.add(cbbNuocSX); box2.add(Box.createHorizontalStrut(5));

		box3.add(lbDangBaoChe); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbDangBaoChe); box3.add(Box.createHorizontalStrut(5));
		box3.add(lbPhanLoai); box3.add(Box.createHorizontalStrut(5));
		box3.add(cbbPhanLoai); box3.add(Box.createHorizontalStrut(5));

		btnTimKiem = new ColoredButton("Tìm", new ImageIcon("Images/search.png")); btnTimKiem.setFont(UIConstant.NORMAL_FONT);

		btnTimKiem.setBackground(UIConstant.PRIMARY_COLOR);

		box4.add(Box.createHorizontalGlue());
		box4.add(btnTimKiem);
		box4.add(Box.createHorizontalGlue());
	}

	public void taiDuLieuLenBang(List<Thuoc> dsThuoc, int minIndex) {
		if(minIndex >= dsThuoc.size() || minIndex < 0)
			return;
		
		lbPage.setText("Trang " + (minIndex / 20 + 1) + " trong " + ((dsThuoc.size() - 1) / 20 + 1) + " trang.");
		modelThuoc.setRowCount(0);

		for(int i = minIndex; i < minIndex + 20; i++) {
			if(i >= dsThuoc.size())
				break;
			
			Thuoc item = dsThuoc.get(i);
			String ha = "";
			byte[] b = null;
			if(item.getHinhAnh() == null)
				ha =  "Images/image.png";
			else {
				try {
					Blob blob = item.getHinhAnh();

					b = blob.getBytes(1, (int) blob.length());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			modelThuoc.addRow(new Object[] {
					ha != "" ? ha : b, item.getMaThuoc(), item.getTenThuoc(),
							item.getNhaCungCap() == null ? "" : item.getNhaCungCap().getTenNCC(), item.getQuyCachDongGoi(),
									item.getDangBaoChe(), item.getDonViTinh(),
									item.getDonGia(), item.getPhanLoai() == PhanLoai.THUOC_BAN_THEO_DON ? "Thuốc có kê đơn" : "Thuốc không kê đơn",
											item.getNuocSanXuat(), item.isTrangThai() ? "Đang bán" : "Ngừng bán", item.kiemTraSoLuong()
			});
		}
		
		currentIndex = minIndex;
	}

	private void addEvent() {
		txtTen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timThuoc();
			}
		});
		txtQCDG.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timThuoc();
			}
		});
		cbbDVT.addItemListener(e -> timThuoc());
		cbbNuocSX.addItemListener(e -> timThuoc());
		cbbDangBaoChe.addItemListener(e -> timThuoc());
		cbbPhanLoai.addItemListener(e -> timThuoc());
		
		btnHome.addActionListener(e -> {
			if(thuocs != null) {
				taiDuLieuLenBang(thuocs, 0);
			}
		});

		btnEnd.addActionListener(e -> {
			if(thuocs != null) {
				taiDuLieuLenBang(thuocs, thuocs.size() - thuocs.size() % 20);
			}
		});

		btnBefore.addActionListener(e -> {
			if(thuocs != null) {
				taiDuLieuLenBang(thuocs, currentIndex   - 20);
			}
		});

		btnNext.addActionListener(e -> {
			if(thuocs != null) {
				taiDuLieuLenBang(thuocs, currentIndex + 20);
			}
		});

		btnChon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableThuoc.getSelectedRow();
				if(row == -1) {
					UIConstant.showInfo(ChonThuocDialog.this, "Chưa chọn thuốc cần thêm!");
					return;
				}
				
				row += currentIndex;

				Thuoc thuoc = thuocs.get(row);

				if(!thuoc.isTrangThai()) {
					UIConstant.showInfo(ChonThuocDialog.this, "Thuốc không còn bán nữa!");
					return;
				}

				int soLuong;
				double donGia;

				do {
					String sl = JOptionPane.showInputDialog(ChonThuocDialog.this, "Nhập số lượng cần thêm", 1);

					if(sl == null)
						return;

					try {
						soLuong = Integer.parseInt(sl);

						if(soLuong <= 0) {
							UIConstant.showInfo(ChonThuocDialog.this, "Số lượng phải lớn hơn 0!");
							continue;
						}

						int slTon = thuoc.kiemTraSoLuong(soLuong);

						if(slTon != -1) {
							UIConstant.showWarning(ChonThuocDialog.this, "Số lượng không đủ, chỉ còn " + slTon + "!");
							continue;
						}

						break;
					} catch (Exception e2) {
						UIConstant.showError(ChonThuocDialog.this, "Vui lòng chỉ nhập số nguyên!");
					}
				}while(true);

				do {
					String dg = JOptionPane.showInputDialog(ChonThuocDialog.this, "Nhập đơn giá", thuoc.getDonGia());

					if(dg == null)
						return;

					try {
						donGia = Double.parseDouble(dg);
						if(donGia <= 0)
							throw new NumberFormatException();
						break;
					} catch (Exception e2) {
						UIConstant.showError(ChonThuocDialog.this, "Đơn giá phải là số lớn hơn 0!");
					}
				}while(true);

				owner.themChiTietHD(thuoc, soLuong, donGia);
			}
		});

		btnQuayLai.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChonThuocDialog.this.dispose();
				owner.setEnabled(true);
				owner.setVisible(true);
			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timThuoc();
			}
		});
	}
}
