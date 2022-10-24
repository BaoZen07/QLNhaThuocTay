package main.java.ui;

import com.toedter.calendar.JDateChooser;
import main.java.dao.NhanVienDAO;
import main.java.entity.NhanVien;
import net.java.balloontip.BalloonTip;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuanLyNhanVienPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel defaultTable;
	private CustomTable tableNV;
	private JScrollPane scroll;
	private JPasswordField txtNewPasssWord;
	private JPasswordField txtConfirmPassWord;
	private ColoredButton btnHide;
	private ColoredButton btnChange;
	private JComboBox<String> jcbLoaiNV;
	private ColoredButton btnThem;
	private ColoredButton btnSua;
	private ColoredButton btnQuayLai;
	private ColoredButton btnXoa;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtDiaChi;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private JDateChooser dateNgayVaoLam;
	private JDateChooser dateNgaySinh;
	private JComboBox<String> jcbGioiTinh;
	private ColoredButton btnTim;
	private NhanVienDAO dao_NV;
	private MainFrame mainFrame;
	private JTextField txtTKTenNV;
	private JTextField txtTKDiaChi;
	private JTextField txtTKEmail;
	private JTextField txtTKSoDienThoai;
	private JDateChooser dateTKNgayVaoLam;
	private JComboBox<String> jcbTKLoaiNV;
	private ColoredButton btnXoaRong2;
	private ColoredButton btnXoaRong1;
	private List<NhanVien> dsNVs;
	private BalloonTip ballNgaySinh;
	private BalloonTip ballTenNV;
	private BalloonTip ballNgayVaoLam;
	private BalloonTip ballDiaChi;
	private BalloonTip ballEmail;
	private BalloonTip ballSoDienThoai;

	private JTabbedPane tabPaneTT;

	public QuanLyNhanVienPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setOpaque(true);
		setLookAndFeel();
		setLayout(new BorderLayout());
		setBackground(Color.white);

		addNorth();
		addCenter();
		addEvent();

		getAllComponents(this).forEach(item -> {
			item.addKeyListener(new KeyAdapter() {
				private boolean isCtrlPressed = false;

				@Override
				public void keyPressed(KeyEvent e) {
					if(isCtrlPressed) {
						//Nhấn phím N khi đang giữ phím Ctrl
						if(e.getKeyCode() == KeyEvent.VK_N)
							btnThem.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_X)
							btnXoa.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_LEFT)
							btnQuayLai.doClick();
						else if(e.getKeyCode() == KeyEvent.VK_E) {
							btnXoaRong1.doClick();
							btnXoaRong2.doClick();
						}
						else if(e.getKeyCode() == KeyEvent.VK_F)
							btnTim.doClick();

					} else if(e.getKeyCode() == KeyEvent.VK_CONTROL)
						isCtrlPressed = true;
					else 
						isCtrlPressed = false;
				}

				@Override
				public void keyReleased(KeyEvent e) {
					isCtrlPressed = false;
				}
			});
		});

		dao_NV = new NhanVienDAO();
		loadData();

	}

	public Component getDefaultFocusComponent() {
		return txtTenNV;
	}

	public List<Component> getAllComponents(Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	private void taiDuLieuLenBang(List<NhanVien> dsNV) {
		dsNVs = dsNV;
		defaultTable.getDataVector().removeAllElements();
		defaultTable.fireTableDataChanged();
		new Thread( () -> {

			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			for (NhanVien nhanVien : dsNVs) {

				SwingUtilities.invokeLater(() -> {
					String loainv = "";
					if(nhanVien.getLoaiNhanVien() == 0)
						loainv = "Quản lý viên";
					else 
						loainv = "Nhân viên bán thuốc";

					String gioitinh = "";
					if(nhanVien.isGioiTinh() == true)
						gioitinh = "Nam";
					else 
						gioitinh = "Nữ";

					defaultTable.addRow(new Object[] {
							nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien(),
							nhanVien.getNgaySinh().format(format), nhanVien.getDiaChi(), gioitinh,
							nhanVien.getEmail(), nhanVien.getSoDienThoai(), loainv, 
							nhanVien.getNgayVaoLam().format(format), nhanVien.getMatKhau()
					});
				});

			}
		}).start();;
	}

	private void loadData() {

		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		dsNV = dao_NV.lay20NhanVienGanDay();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (NhanVien nhanVien : dsNV) {
			String loainv = "";
			if(nhanVien.getLoaiNhanVien() == 0)
				loainv = "Quản lý viên";
			else 
				loainv = "Nhân viên bán thuốc";

			String gioitinh = "";
			if(nhanVien.isGioiTinh() == true)
				gioitinh = "Nam";
			else 
				gioitinh = "Nữ";

			defaultTable.addRow(new Object[] {
					nhanVien.getMaNhanVien(), nhanVien.getTenNhanVien(),
					nhanVien.getNgaySinh().format(format), nhanVien.getDiaChi(), gioitinh,
					nhanVien.getEmail(), nhanVien.getSoDienThoai(), loainv, 
					nhanVien.getNgayVaoLam().format(format), nhanVien.getMatKhau()
			});
		}
	}

	private void addNorth() {

		JLabel lblTitle = new JLabel("Quản lý bhân viên");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(UIConstant.PRIMARY_COLOR);
		lblTitle.setHorizontalAlignment(JLabel.CENTER);

		JPanel pnlNorth = new JPanel();
		this.add(pnlNorth = new JPanel(new BorderLayout()), BorderLayout.NORTH);
		pnlNorth.setOpaque(false);

		tabPaneTT = new JTabbedPane();
		tabPaneTT.setOpaque(false);
		tabPaneTT.setPreferredSize(new Dimension(100, 230));

		pnlNorth.add(tabPaneTT, BorderLayout.CENTER);

		//Tab Thông tin
		Box boxLine1, boxLine2, boxLine3, boxLine4, boxThongTin;
		boxLine1 = Box.createHorizontalBox();
		boxLine2 = Box.createHorizontalBox();
		boxLine3 = Box.createHorizontalBox();
		boxLine4 = Box.createHorizontalBox();
		boxThongTin = Box.createVerticalBox();

		JLabel lblMaNV = new JLabel("Mã nhân viên:");
		JLabel lblTenNV = new JLabel("Tên nhân viên:");
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		JLabel lblLoaiNV = new JLabel("Loại nhân viên:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		JLabel lblNgayVaoLam = new JLabel("Ngày vào làm:");

		lblMaNV.setFont(UIConstant.NORMAL_FONT);
		lblMaNV.setPreferredSize(new Dimension(90, 20));
		lblTenNV.setFont(UIConstant.NORMAL_FONT);
		lblTenNV.setPreferredSize(new Dimension(90, 20));
		lblNgaySinh.setFont(UIConstant.NORMAL_FONT);
		lblNgaySinh.setPreferredSize(new Dimension(90, 20));
		lblDiaChi.setFont(UIConstant.NORMAL_FONT);
		lblDiaChi.setPreferredSize(new Dimension(90, 20));
		lblGioiTinh.setFont(UIConstant.NORMAL_FONT);
		lblGioiTinh.setPreferredSize(new Dimension(90, 20));
		lblLoaiNV.setFont(UIConstant.NORMAL_FONT);
		lblLoaiNV.setPreferredSize(new Dimension(90, 20));
		lblEmail.setFont(UIConstant.NORMAL_FONT);
		lblEmail.setPreferredSize(new Dimension(90, 20));
		lblSoDienThoai.setFont(UIConstant.NORMAL_FONT);
		lblSoDienThoai.setPreferredSize(new Dimension(90, 20));
		lblNgayVaoLam.setFont(UIConstant.NORMAL_FONT);
		lblNgayVaoLam.setPreferredSize(new Dimension(90, 20));

		txtMaNV = new JTextField();
		txtMaNV.setEditable(false);
		txtMaNV.setFont(UIConstant.NORMAL_FONT);
		txtTenNV = new JTextField();
		txtTenNV.setFont(UIConstant.NORMAL_FONT);
		txtDiaChi = new JTextField();
		txtDiaChi.setFont(UIConstant.NORMAL_FONT);
		txtEmail = new JTextField();
		txtEmail.setFont(UIConstant.NORMAL_FONT);
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(UIConstant.NORMAL_FONT);

		dateNgayVaoLam = new JDateChooser();
		dateNgayVaoLam.setDateFormatString("dd-MM-yyyy");
		dateNgayVaoLam.setFont(UIConstant.NORMAL_FONT);
		dateNgaySinh = new JDateChooser();
		dateNgaySinh.setDateFormatString("dd-MM-yyyy");
		dateNgaySinh.setFont(UIConstant.NORMAL_FONT);

		jcbGioiTinh = new JComboBox<String>();
		jcbGioiTinh.setFont(UIConstant.NORMAL_FONT);
		jcbGioiTinh.addItem("Nam");
		jcbGioiTinh.addItem("Nữ");
		jcbLoaiNV = new JComboBox<String>();
		jcbLoaiNV.setFont(UIConstant.NORMAL_FONT);
		jcbLoaiNV.addItem("Nhân viên bán thuốc");
		jcbLoaiNV.addItem("Quản lý viên");

		boxLine1.add(Box.createHorizontalStrut(10));
		boxLine1.add(lblMaNV);
		boxLine1.add(txtMaNV);
		boxLine1.add(Box.createHorizontalStrut(10));
		boxLine1.add(lblNgaySinh);
		boxLine1.add(dateNgaySinh);
		dateNgaySinh.setPreferredSize(new Dimension(100, 20));
		txtMaNV.setPreferredSize(new Dimension(390, 20));
		boxLine1.add(Box.createHorizontalStrut(10));

		boxLine2.add(Box.createHorizontalStrut(10));
		boxLine2.add(lblTenNV);
		boxLine2.add(txtTenNV);
		boxLine2.add(Box.createHorizontalStrut(10));
		boxLine2.add(lblNgayVaoLam);
		boxLine2.add(dateNgayVaoLam);
		dateNgayVaoLam.setPreferredSize(new Dimension(100, 20));
		txtTenNV.setPreferredSize(new Dimension(390, 20));
		boxLine2.add(Box.createHorizontalStrut(10));

		boxLine3.add(Box.createHorizontalStrut(10));
		boxLine3.add(lblDiaChi);
		boxLine3.add(txtDiaChi);
		boxLine3.add(Box.createHorizontalStrut(10));
		boxLine3.add(lblGioiTinh);
		boxLine3.add(jcbGioiTinh);
		boxLine3.add(Box.createHorizontalStrut(10));

		boxLine4.add(Box.createHorizontalStrut(10));
		boxLine4.add(lblEmail);
		boxLine4.add(txtEmail);
		boxLine4.add(Box.createHorizontalStrut(10));
		boxLine4.add(lblSoDienThoai);
		boxLine4.add(txtSoDienThoai);
		boxLine4.add(Box.createHorizontalStrut(10));
		boxLine4.add(lblLoaiNV);
		boxLine4.add(jcbLoaiNV);
		txtEmail.setPreferredSize(new Dimension(170, 20));
		txtSoDienThoai.setPreferredSize(new Dimension(90, 20));
		boxLine4.add(Box.createHorizontalStrut(10));

		boxThongTin.add(Box.createVerticalStrut(10));
		boxThongTin.add(boxLine1);
		boxThongTin.add(Box.createVerticalStrut(10));
		boxThongTin.add(boxLine2);
		boxThongTin.add(Box.createVerticalStrut(10));
		boxThongTin.add(boxLine3);
		boxThongTin.add(Box.createVerticalStrut(10));
		boxThongTin.add(boxLine4);
		boxThongTin.add(Box.createVerticalStrut(10));

		Box boxButton = Box.createHorizontalBox();
		btnThem = addButtonTo(boxButton, "Thêm nhân viên", "Images/add.png", UIConstant.PRIMARY_COLOR);
		btnXoaRong1 = addButtonTo(boxButton, "Xoá rỗng", "Images/empty.png", UIConstant.DANGER_COLOR);

		boxThongTin.add(Box.createVerticalStrut(5));
		boxThongTin.add(boxButton);
		boxThongTin.add(Box.createVerticalStrut(10));
		
		ballNgaySinh = new BalloonTip(dateNgaySinh, "Ngày sinh phải trước ngày hiện tại"); ballNgaySinh.setVisible(false); ballNgaySinh.setCloseButton(null);
		ballTenNV = new BalloonTip(txtTenNV, " + Họ và tên nhân viên phải bắt đầu chữ cái in hoa \n"

							+ " + Không chứa các ký tự đặc biệt và số"); ballTenNV.setVisible(false); ballTenNV.setCloseButton(null);
		ballNgayVaoLam = new BalloonTip(dateNgayVaoLam, "Ngày vào làm phải trước ngày hiện tại và sau ngày sinh"); ballNgayVaoLam.setVisible(false); ballNgayVaoLam.setCloseButton(null);
		ballDiaChi = new BalloonTip(txtDiaChi, "Địa chỉ không được rỗng"); ballDiaChi.setVisible(false); ballDiaChi.setCloseButton(null);
		ballEmail = new BalloonTip(txtEmail, " + Email phải bắt đầu bằng 1 ký tự \n"
				+ "+ Chỉ chứa ký tự a-z, 0-9 và ký tự dấu chấm (.) \n" 
				+ "+ Độ dài tối thiểu là 8, độ dài tối đa là 32 \n"
				+ "+ Tên miền có thể là tên miền cấp 1 hoặc cấp 2"); ballEmail.setVisible(false); ballEmail.setCloseButton(null);
		ballSoDienThoai = new BalloonTip(txtSoDienThoai, "Số điện thoại không được rỗng, gồm 10 số, bắt đầu bằng số 0"); ballSoDienThoai.setVisible(false); ballSoDienThoai.setCloseButton(null);
		
		//Tab tìm kiếm

		Box boxLineTK2, boxLineTK3, boxLineTK4, boxTimKiem;
		boxLineTK2 = Box.createHorizontalBox();
		boxLineTK3 = Box.createHorizontalBox();
		boxLineTK4 = Box.createHorizontalBox();
		boxTimKiem = Box.createVerticalBox();

		JLabel lblTKTenNV = new JLabel("Tên nhân viên:");
		JLabel lblTKDiaChi = new JLabel("Địa chỉ:");
		JLabel lblTKLoaiNV = new JLabel("Loại nhân viên:");
		JLabel lblTKEmail = new JLabel("Email:");
		JLabel lblTKSoDienThoai = new JLabel("Số điện thoại:");
		JLabel lblTKNgayVaoLam = new JLabel("Ngày vào làm:");

		lblTKTenNV.setFont(UIConstant.NORMAL_FONT);
		lblTKTenNV.setPreferredSize(new Dimension(90, 20));
		lblTKDiaChi.setFont(UIConstant.NORMAL_FONT);
		lblTKDiaChi.setPreferredSize(new Dimension(90, 20));
		lblTKLoaiNV.setFont(UIConstant.NORMAL_FONT);
		lblTKLoaiNV.setPreferredSize(new Dimension(90, 20));
		lblTKEmail.setFont(UIConstant.NORMAL_FONT);
		lblTKEmail.setPreferredSize(new Dimension(90, 20));
		lblTKSoDienThoai.setFont(UIConstant.NORMAL_FONT);
		lblTKSoDienThoai.setPreferredSize(new Dimension(90, 20));
		lblTKNgayVaoLam.setFont(UIConstant.NORMAL_FONT);
		lblTKNgayVaoLam.setPreferredSize(new Dimension(90, 20));

		txtTKTenNV = new JTextField();
		txtTKTenNV.setFont(UIConstant.NORMAL_FONT);
		txtTKDiaChi = new JTextField();
		txtTKDiaChi.setFont(UIConstant.NORMAL_FONT);
		txtTKEmail = new JTextField();
		txtTKEmail.setFont(UIConstant.NORMAL_FONT);
		txtTKSoDienThoai = new JTextField();
		txtTKSoDienThoai.setFont(UIConstant.NORMAL_FONT);

		dateTKNgayVaoLam = new JDateChooser();
		dateTKNgayVaoLam.setDateFormatString("dd-MM-yyyy");
		dateTKNgayVaoLam.setFont(UIConstant.NORMAL_FONT);

		jcbTKLoaiNV = new JComboBox<String>();
		jcbTKLoaiNV.setFont(UIConstant.NORMAL_FONT);
		jcbTKLoaiNV.addItem("");
		jcbTKLoaiNV.addItem("Nhân viên bán thuốc");
		jcbTKLoaiNV.addItem("Quản lý viên");

		boxLineTK2.add(Box.createHorizontalStrut(10));
		boxLineTK2.add(lblTKTenNV);
		boxLineTK2.add(txtTKTenNV);
		boxLineTK2.add(Box.createHorizontalStrut(10));
		boxLineTK2.add(lblTKNgayVaoLam);
		boxLineTK2.add(dateTKNgayVaoLam);
		dateTKNgayVaoLam.setPreferredSize(new Dimension(100, 20));
		txtTKTenNV.setPreferredSize(new Dimension(390, 20));
		boxLineTK2.add(Box.createHorizontalStrut(10));

		boxLineTK3.add(Box.createHorizontalStrut(10));
		boxLineTK3.add(lblTKDiaChi);
		boxLineTK3.add(txtTKDiaChi);
		boxLineTK3.add(Box.createHorizontalStrut(10));

		boxLineTK4.add(Box.createHorizontalStrut(10));
		boxLineTK4.add(lblTKEmail);
		boxLineTK4.add(txtTKEmail);
		boxLineTK4.add(Box.createHorizontalStrut(10));
		boxLineTK4.add(lblTKSoDienThoai);
		boxLineTK4.add(txtTKSoDienThoai);
		boxLineTK4.add(Box.createHorizontalStrut(10));
		boxLineTK4.add(lblTKLoaiNV);
		boxLineTK4.add(jcbTKLoaiNV);
		txtTKEmail.setPreferredSize(new Dimension(170, 20));
		txtTKSoDienThoai.setPreferredSize(new Dimension(90, 20));
		boxLineTK4.add(Box.createHorizontalStrut(10));

		boxTimKiem.add(Box.createVerticalStrut(20));
		boxTimKiem.add(boxLineTK2);
		boxTimKiem.add(Box.createVerticalStrut(20));
		boxTimKiem.add(boxLineTK3);
		boxTimKiem.add(Box.createVerticalStrut(20));
		boxTimKiem.add(boxLineTK4);
		boxTimKiem.add(Box.createVerticalStrut(20));

		Box boxTKButton = Box.createHorizontalBox();
		btnTim = addButtonTo(boxTKButton, "Tìm", "Images/search.png", UIConstant.PRIMARY_COLOR);
		btnXoaRong2 = addButtonTo(boxTKButton, "Xoá rỗng", "Images/empty.png", UIConstant.DANGER_COLOR);

		boxTimKiem.add(Box.createVerticalStrut(5));
		boxTimKiem.add(boxTKButton);
		boxTimKiem.add(Box.createVerticalStrut(10));

		tabPaneTT.addTab("Tìm kiếm", boxTimKiem);
		tabPaneTT.add("Cập nhật nhân viên", boxThongTin);
		tabPaneTT.setFont(new Font("Arial", Font.BOLD, 12));

	}

	private void addCenter() {

		// Center
		JPanel pnlCenter = new JPanel();
		this.add(pnlCenter = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		pnlCenter.setOpaque(false);

		JTabbedPane tabPaneNCC = new JTabbedPane();
		tabPaneNCC.setOpaque(false);
		tabPaneNCC.setPreferredSize(new Dimension(100, 200));

		pnlCenter.add(tabPaneNCC, BorderLayout.CENTER);

		String[] header = { "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Giới tính", "Email",
				"Số điện thoại", "Loại nhân viên", "Ngày vào làm" };
		defaultTable = new DefaultTableModel(header, 0);
		tableNV = new CustomTable();
		tableNV.setModel(defaultTable);
		scroll = new JScrollPane(tableNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.getViewport().setBackground(Color.white);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		tabPaneNCC.addTab("Danh sách nhân viên", scroll);
		tabPaneNCC.setFont(new Font("Arial", Font.BOLD, 12));

		JPanel pnlSouth = new JPanel(new BorderLayout());

		pnlSouth.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		pnlSouth.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

		Box boxLineButton;

		boxLineButton = Box.createHorizontalBox();
		btnXoa = addButtonTo(boxLineButton, "Xoá nhân viên", "Images/delete.png", UIConstant.DANGER_COLOR);
		btnSua = addButtonTo(boxLineButton, "Sửa nhân viên", "Images/modify.png", UIConstant.WARNING_COLOR);
		btnQuayLai = addButtonTo(boxLineButton, "Quay Lại", "Images/back.png", UIConstant.PRIMARY_COLOR);

		pnlSouth.setOpaque(false);
		pnlSouth.add(boxLineButton, BorderLayout.CENTER);
		pnlCenter.add(pnlSouth, BorderLayout.SOUTH);

		// Below Center (Password)
		JPanel pnlBelowCenter = new JPanel();
		this.add(pnlBelowCenter = new JPanel(new BorderLayout()), BorderLayout.SOUTH);
		pnlBelowCenter.setOpaque(false);

		JTabbedPane tabPanePassword = new JTabbedPane();
		tabPanePassword.setOpaque(false);
		tabPanePassword.setPreferredSize(new Dimension(100, 175));

		Box boxPassword, boxNorth, boxButton;
		boxPassword = Box.createVerticalBox();
		boxNorth = Box.createHorizontalBox();
		boxButton = Box.createHorizontalBox();

		boxNorth.add(Box.createHorizontalStrut(10));

		boxPassword.add(Box.createVerticalStrut(5));
		boxPassword.add(boxNorth, BorderLayout.SOUTH);
		boxPassword.add(Box.createVerticalStrut(5));
		txtNewPasssWord = addInputItemTo(boxPassword, "Mật khẩu mới:");
		txtConfirmPassWord = addInputItemTo(boxPassword, "Xác nhận:");
		boxPassword.add(Box.createVerticalStrut(5));

		btnChange = addButtonTo(boxButton, "Đổi mật khẩu", "Images/changePassword.png", UIConstant.WARNING_COLOR);
		btnHide = addButtonTo(boxButton, "Hiện", "Images/show.png", UIConstant.PRIMARY_COLOR);
		btnHide.setPreferredSize(new Dimension(90, 50));
		boxPassword.add(boxButton);
		boxPassword.add(Box.createVerticalStrut(5));

		tabPanePassword.addTab("Đổi mật khẩu", boxPassword);
		tabPanePassword.setFont(new Font("Arial", Font.BOLD, 12));
		pnlBelowCenter.add(tabPanePassword, BorderLayout.CENTER);
		pnlBelowCenter.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

	}

	private void addEvent() {
		this.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("ancestor")) {
					tabPaneTT.setSelectedIndex(0);
				}
			}
		});
		
		dateNgaySinh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(dateNgaySinh.getDate() == null || dateNgaySinh.getDate().after(new Date()))
					ballNgaySinh.setVisible(true);
				else 
					ballNgaySinh.setVisible(false);
			}
		});
		
		txtTenNV.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtTenNV.getText().isEmpty() || !txtTenNV.getText().matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*"))
					ballTenNV.setVisible(true);
				else 
					ballTenNV.setVisible(false);
			}
		});
		
		dateNgayVaoLam.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(dateNgayVaoLam.getDate() == null || dateNgayVaoLam.getDate().after(new Date())
						|| dateNgayVaoLam.getDate().before(dateNgaySinh.getDate()))
					ballNgayVaoLam.setVisible(true);
				else 
					ballNgayVaoLam.setVisible(false);
			}
		});
		
		txtDiaChi.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtDiaChi.getText().isEmpty())
					ballDiaChi.setVisible(true);
				else 
					ballDiaChi.setVisible(false);
			}
		});
		
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!txtEmail.getText().isEmpty())
					if(!txtEmail.getText().matches("^[a-z][a-z0-9\\.]{7,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$"))
						ballEmail.setVisible(true);
				else 
					ballDiaChi.setVisible(false);
			}
		});
		
		
		txtSoDienThoai.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtSoDienThoai.getText().isEmpty() || !txtSoDienThoai.getText().matches("0\\d{9}"))
					ballSoDienThoai.setVisible(true);
				else 
					ballSoDienThoai.setVisible(false);
			}
		});
		
		txtTKDiaChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNV();
			}
		});
		
		txtTKEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNV();
			}
		});
		
		txtTKSoDienThoai.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNV();
			}
		});
		
		txtTKTenNV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				timNV();
			}
		});
		
		
		tableNV.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (tableNV.getSelectedRow() == -1)
						return;
					int row = tableNV.getSelectedRow();

					String temp = defaultTable.getValueAt(row, 2).toString();
					Date date = new SimpleDateFormat("dd-MM-yyyy").parse(temp);

					String temp2 = defaultTable.getValueAt(row, 8).toString();
					Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(temp2);

					txtMaNV.setText(defaultTable.getValueAt(row, 0).toString());
					txtTenNV.setText(defaultTable.getValueAt(row, 1).toString());
					dateNgaySinh.setDate(date);
					txtDiaChi.setText(defaultTable.getValueAt(row, 3).toString());
					jcbGioiTinh.setSelectedItem(defaultTable.getValueAt(row, 4));
					txtEmail.setText(defaultTable.getValueAt(row, 5).toString());
					txtSoDienThoai.setText(defaultTable.getValueAt(row, 6).toString());
					jcbLoaiNV.setSelectedItem(defaultTable.getValueAt(row, 7));
					dateNgayVaoLam.setDate(date2);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validData()) {

					boolean gt;
					if(jcbGioiTinh.getSelectedItem().toString().equals("Nam"))
						gt = true;
					else
						gt = false;

					int lnv;
					if(jcbLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Nhân viên bán thuốc"))
						lnv = 1;
					else
						lnv = 0;

					Date dateNS = dateNgaySinh.getDate();
					LocalDate lcdNS = dateNS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					Date dateNVL = dateNgayVaoLam.getDate();
					LocalDate lcdNVL = dateNVL.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					NhanVien nv = new NhanVien(txtTenNV.getText(),
							lcdNS, txtDiaChi.getText(),
							gt, txtEmail.getText() == "" ? null : txtEmail.getText(),
									txtSoDienThoai.getText(), lcdNVL,lnv);

					if(dao_NV.addNhanVien(nv)) {

						String loainv = "";
						if(lnv == 0)
							loainv = "Quản lý viên";
						else 
							loainv = "Nhân viên bán thuốc";

						String gioitinh = "";
						if(gt == true)
							gioitinh = "Nam";
						else 
							gioitinh = "Nữ";

						DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

						UIConstant.showInfo(QuanLyNhanVienPanel.this, "Thêm thành công");
						defaultTable.addRow(new Object[] {
								nv.getMaNhanVien(), nv.getTenNhanVien(),
								nv.getNgaySinh().format(format), nv.getDiaChi(), gioitinh,
								nv.getEmail(), nv.getSoDienThoai(), loainv, 
								nv.getNgayVaoLam().format(format), nv.getMatKhau()
						});

						XoaRong();
					} else
						UIConstant.showWarning(QuanLyNhanVienPanel.this, "Thêm không thành công");
				}
			}		
		});

		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableNV.getSelectedRow();
				if (row == -1) {
					UIConstant.showInfo(QuanLyNhanVienPanel.this, "Chưa chọn nhân viên!");
					return;
				} 

				if(JOptionPane.showConfirmDialog(QuanLyNhanVienPanel.this, "Bạn có chắc là muốn xóa!", "Xác nhận", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
					return;
				}

				if(dao_NV.deleteNhanVien(txtMaNV.getText().toString())) {

					UIConstant.showInfo(QuanLyNhanVienPanel.this, "Xoá thành công");

					defaultTable.removeRow(row);
					XoaRong();
				} else {
					UIConstant.showWarning(QuanLyNhanVienPanel.this, "Xoá không thành công");

				}
			}
		});

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabPaneTT.getSelectedIndex() == 0)
					tabPaneTT.setSelectedIndex(1);
				else {
					if (tableNV.getSelectedRow() == -1) {
						UIConstant.showInfo(QuanLyNhanVienPanel.this, "Chưa chọn nhân viên!");
						return;
					} else {
						if(validData()) {
							boolean gt;
							if(jcbGioiTinh.getSelectedItem().toString().equals("Nam"))
								gt = true;
							else
								gt = false;

							int lnv;
							if(jcbLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Quản lý viên"))
								lnv = 0;
							else
								lnv = 1;

							Date dateNS = dateNgaySinh.getDate();
							LocalDate lcdNS = dateNS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

							Date dateNVL = dateNgayVaoLam.getDate();
							LocalDate lcdNVL = dateNVL.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

							NhanVien nv = new NhanVien(txtTenNV.getText(),
									lcdNS, txtDiaChi.getText(),
									gt, txtEmail.getText(),
									txtSoDienThoai.getText(), lcdNVL, lnv);

							String manv = tableNV.getValueAt(tableNV.getSelectedRow(), 0).toString();
							nv.setMaNhanVien(manv);

							if(dao_NV.updateNhanVien(nv)) {

								String loainv = "";
								if(lnv == 0)
									loainv = "Quản lý viên";
								else 
									loainv = "Nhân viên bán thuốc";

								String gioitinh = "";
								if(gt == true)
									gioitinh = "Nam";
								else 
									gioitinh = "Nữ";

								UIConstant.showInfo(QuanLyNhanVienPanel.this, "Sửa thành công");

								defaultTable.removeRow(tableNV.getSelectedRow());

								DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

								defaultTable.addRow(new Object[] {
										nv.getMaNhanVien(), nv.getTenNhanVien(),
										nv.getNgaySinh().format(format), nv.getDiaChi(), gioitinh,
										nv.getEmail(), nv.getSoDienThoai(), loainv, 
										nv.getNgayVaoLam().format(format), nv.getMatKhau()
								});

								XoaRong();
							} else
								UIConstant.showWarning(QuanLyNhanVienPanel.this, "Sửa không thành công");
						}
					}
				}
			}
		});

		btnTim.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				timNV();
			}
		});

		btnQuayLai.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.changeCenter(mainFrame.getTrangChuPanel());
			}
		});

		btnHide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon hideIcon = new ImageIcon("Images/hide.png");
				String hideText = "Ẩn";
				ImageIcon showIcon = new ImageIcon("Images/show.png");
				String showText = "Hiện";
				if (btnHide.getText().equalsIgnoreCase(hideText)) {
					btnHide.setIcon(showIcon);
					btnHide.setText(showText);
					txtConfirmPassWord.setEchoChar('*');
					txtNewPasssWord.setEchoChar('*');
				} else {
					btnHide.setIcon(hideIcon);
					btnHide.setText(hideText);
					txtConfirmPassWord.setEchoChar((char) 0);
					txtNewPasssWord.setEchoChar((char) 0);
				}
			}
		});

		btnXoaRong1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				XoaRong();
			}
		});

		btnXoaRong2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				XoaRongTK();
			}
		});

		btnChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableNV.getSelectedRow() == -1) {
					UIConstant.showWarning(QuanLyNhanVienPanel.this, "Chưa chọn nhân viên");
					return;
				} else {

					if (String.valueOf(txtNewPasssWord.getPassword()).equals("")
							|| String.valueOf(txtConfirmPassWord.getPassword()).equals(""))
						UIConstant.showWarning(QuanLyNhanVienPanel.this, "Không được bỏ trống");
					else if (!String.valueOf(txtNewPasssWord.getPassword())
							.equals(String.valueOf(txtConfirmPassWord.getPassword())))
						UIConstant.showWarning(QuanLyNhanVienPanel.this, "Xác nhận mật khẩu không thành công");
					else {
						NhanVien nv = dao_NV.getNhanVien(txtMaNV.getText().toString());
						nv.setMatKhau(String.valueOf(txtConfirmPassWord.getPassword()));

						if(dao_NV.updateNhanVien(nv)) {
							UIConstant.showInfo(QuanLyNhanVienPanel.this, "Đổi mật khẩu thành công");
							XoaRong();
						} else
							UIConstant.showWarning(QuanLyNhanVienPanel.this, "Đổi mật khẩu không thành công");
					}
				}
			}
		});

	}

	private void timNV() {
		new Thread( () -> {
			List<NhanVien> list = new ArrayList<NhanVien>();

			int lnv;

			if(jcbTKLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Quản lý viên"))
				lnv = 0;
			else if(jcbTKLoaiNV.getSelectedItem().toString().equalsIgnoreCase("Nhân viên bán thuốc"))
				lnv = 1;
			else 
				lnv = 2;

			LocalDate lcdNVL = null;

			if(dateTKNgayVaoLam.getDate() != null)
				lcdNVL = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(dateTKNgayVaoLam.getDate()) );

			list = dao_NV.findNhanVien(txtTKTenNV.getText(), txtTKDiaChi.getText(), 
					txtTKEmail.getText(), txtTKSoDienThoai.getText(),
					lcdNVL, lnv);

			if(list.size() != 0) {
				taiDuLieuLenBang(list);

			} else {
				dsNVs.clear();
				defaultTable.setRowCount(0);
			}
		}).start();;
		
	}

	private ColoredButton addButtonTo(Box box, String name, String path, Color color) {
		ColoredButton btn = new ColoredButton(name, new ImageIcon(path));
		btn.setFont(UIConstant.NORMAL_FONT);
		btn.setBackground(color);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(Box.createHorizontalGlue());
		boxButton.add(btn);
		boxButton.add(Box.createHorizontalGlue());

		box.add(boxButton);

		return btn;
	}

	private JPasswordField addInputItemTo(Box box, String name) {
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(110, 25));
		label.setFont(UIConstant.NORMAL_FONT);
		JPasswordField text = new JPasswordField();

		Box boxItem = Box.createHorizontalBox();
		boxItem.add(Box.createHorizontalStrut(15));
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(label);
		boxItem.add(Box.createHorizontalStrut(5));
		boxItem.add(text);
		boxItem.add(Box.createHorizontalGlue());
		boxItem.add(Box.createHorizontalStrut(15));

		box.add(Box.createVerticalStrut(5));
		box.add(boxItem);
		box.add(Box.createVerticalStrut(5));

		return text;
	}

	private void setLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals("Windows")) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
					break;

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

			}
		}
	}


	private void XoaRong() {
		if (tableNV.getSelectedRow() != -1)
			tableNV.clearSelection();
		txtTenNV.setText("");
		txtMaNV.setText("");
		txtEmail.setText("");
		txtDiaChi.setText("");
		txtSoDienThoai.setText("");
		jcbGioiTinh.setSelectedItem("");
		jcbLoaiNV.setSelectedItem("");
		dateNgaySinh.setCalendar(null);
		dateNgayVaoLam.setCalendar(null);
		txtConfirmPassWord.setText("");
		txtNewPasssWord.setText("");
		
		ballNgaySinh.setVisible(false);
		ballTenNV.setVisible(false);
		ballNgayVaoLam.setVisible(false);
		ballDiaChi.setVisible(false);
		ballEmail.setVisible(false);
		ballSoDienThoai.setVisible(false);
		
		
		txtMaNV.requestFocus();
	}
	
	private void XoaRongTK() {
		txtTKTenNV.setText("");
		txtTKEmail.setText("");
		txtTKDiaChi.setText("");
		txtTKSoDienThoai.setText("");
		jcbTKLoaiNV.setSelectedItem("");
		dateTKNgayVaoLam.setCalendar(null);
		txtConfirmPassWord.setText("");
		txtNewPasssWord.setText("");
		txtTKTenNV.requestFocus();
	}

	private boolean validData() {

		String tennv = txtTenNV.getText().trim();
		String diachi = txtDiaChi.getText().trim();
		String sodt = txtSoDienThoai.getText().trim();
		String email = txtEmail.getText().trim();

		// Ngày sinh: Ngày sinh phải trước ngày hiện tại
		if (dateNgaySinh.getDate() == null || dateNgaySinh.getDate().after(new Date())) {
			ballNgaySinh.setVisible(true);
			return false;
		}else
			ballNgaySinh.setVisible(false);
		
		// Tên nhân viên: Không được rỗng, không chứa ký tự đặc biệt
		if (tennv.isEmpty() || !tennv.matches("[\\p{Lu}[A-Z]][\\p{L}[a-z]]*(\\s+[\\p{Lu}[A-Z]][\\p{L}[a-z]]*)*")) {
			ballTenNV.setVisible(true);
			return false;
		}else
			ballTenNV.setVisible(false);
		
		// Ngày vào làm: Trước ngày hiện tại và sau ngày sinh
		if (dateNgayVaoLam.getDate() == null || dateNgayVaoLam.getDate().after(new Date())
				|| dateNgayVaoLam.getDate().before(dateNgaySinh.getDate())) {
			ballNgayVaoLam.setVisible(true);
			return false;
		}else
			ballNgayVaoLam.setVisible(false);

		// Địa chỉ: Không được rỗng
		if (diachi.isEmpty()) {
			ballDiaChi.setVisible(true);
			return false;
		}else
			ballDiaChi.setVisible(false);

		//Email: Được phép rỗng, chỉ được chứa các kí tự '@', '_', '.', '-'
		if(!email.isEmpty()) {
			if(!email.matches("^[a-z][a-z0-9\\.]{7,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
				ballEmail.setVisible(true);
				return false;
			}else
				ballEmail.setVisible(false);
		}
		
		// Số điện thoại: Không được rỗng, gồm 10 số, bắt đầu bằng số 0
		if (sodt.isEmpty() || !sodt.matches("0\\d{9}")) {
			ballSoDienThoai.setVisible(true);
			return false;
		} else
			ballSoDienThoai.setVisible(false);

		return true;

	}
}
